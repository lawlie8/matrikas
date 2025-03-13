import './App.css'
import Header from './pages/global/header/Header'
import MatrikasRouter from './security/MatrikasRouter'

function App() {

  return (
    <section>
      <MatrikasRouter>
        <Header />
      </MatrikasRouter>
    </section>
  )
}

export default App
