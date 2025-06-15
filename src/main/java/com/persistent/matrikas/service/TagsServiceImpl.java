package com.persistent.matrikas.service;
import com.persistent.matrikas.entity.Library;
import com.persistent.matrikas.entity.Tags;
import com.persistent.matrikas.repository.LibraryRepository;
import com.persistent.matrikas.repository.TagsRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;

@Service
public class TagsServiceImpl implements TagsService {

    @Autowired
    TagsRepository tagsRepository;

    @Autowired
    LibraryRepository libraryRepository;

    @Autowired
    mainService m;
    @Override
    public Tags createTags(Tags tag) {
        return tagsRepository.save(tag);
    }

    @Override
    public List<Tags> getTagsByLibraryId(Long id) throws Exception {
        return tagsRepository.findByLibraryId(id);
    }

    @Override
    public List<Tags> getAllTags() {
        return tagsRepository.findAll();
    }

    @Override
    public void deleteTags(Long id) throws Exception {
        if (!tagsRepository.existsById(id)) {
            throw new Exception("Tag not found");
        }
        tagsRepository.deleteById(id);
    }

    // ✅ New function to fetch and store GitHub tags for all libraries
    public void fetchAndStoreGithubTagsForAllLibraries() {
        List<Library> libraries = libraryRepository.findAll();

        for (Library library : libraries) {
            try {
                String apiUrl = library.getApiUrl();

                if (apiUrl != null ) {
                    if(apiUrl.isEmpty()){
                        apiUrl = library.getSideCarsList().get(0).getApiUrl();
                    }
//                    String githubUrl = apiUrl + "/" + source + "/releases"; // Build dynamic URL
//                    System.out.println("Fetching releases from URL: " + githubUrl);

                    // Fetch tags from GitHub API
                    String jsonResponse = fetchGitHubReleases(apiUrl);

                    // Parse and store tags
                    storeTagsFromJson(jsonResponse, library);
                }
            } catch (Exception e) {
                System.err.println("Failed to fetch tags for library: " + library.getId());
                e.printStackTrace();
            }
        }
    }



    // ✅ Fetch GitHub releases using HTTP GET request
    private String fetchGitHubReleases(String url) throws Exception {
        StringBuilder response = new StringBuilder();

        URL apiUrl = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) apiUrl.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Accept", "application/vnd.github.v3+json");

        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
        } else {
            throw new RuntimeException("Failed to fetch releases. Response Code: " + responseCode);
        }

        return response.toString();
    }

    // ✅ Parse and store tags from GitHub API response
    private void storeTagsFromJson(String jsonResponse, Library library) {
        JSONArray releasesArray = new JSONArray(jsonResponse);

        for (int i = 0; i < releasesArray.length(); i++) {
            JSONObject release = releasesArray.getJSONObject(i);
            String tagName = release.getString("tag_name");

            // Store only tags starting with 'v'
            if (tagName.startsWith("v") || Character.isDigit(tagName.charAt(0))) {
                // ✅ Check if tag already exists for the library
                boolean tagExists = tagsRepository.existsByLibraryAndVersion(library, tagName);
                if (!tagExists) {
                    Tags tag = new Tags();
                    tag.setVersion(tagName);
                    tag.setLibrary(library);

                    // ✅ Set release date if available
                    if (release.has("published_at")) {
                        tag.setReleaseDate(LocalDate.parse(release.getString("published_at").substring(0, 10)));
                    } else {
                        tag.setReleaseDate(LocalDate.now()); // Fallback to current date if no release date is provided
                    }

                    // ✅ Save tag to database
                    try{
                        saveData(tag,library);

                    }catch (Exception e){
                        e.printStackTrace();
                    }


                    System.out.println("Stored new tag: " + tagName);
                } else {
                    System.out.println("Tag already exists: " + tagName);
                }
            }
        }
    }


    @Override
    public void fetchAndStoreGitHubTags() {
        fetchAndStoreGithubTagsForAllLibraries(); // Fixed method name
    }

    @Override
    public Tags getTagById(Long id) throws Exception {
        return tagsRepository.findById(id)
                .orElseThrow(() -> new Exception("Tag not found with id: " + id));
    }

    @Transactional
    public void saveData(Tags tag, Library library)
    {
        tagsRepository.save(tag);
        m.scanImage(library.getSource(), tag);
    }

}
