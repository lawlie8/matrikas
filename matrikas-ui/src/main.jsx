import { createRoot } from 'react-dom/client'
import './index.css'
import { BrowserRouter } from 'react-router'
import App from './App.jsx'
import Header from './pages/global/header/Header.jsx'

createRoot(document.getElementById('root')).render(
  <BrowserRouter>
    <Header>

    </Header>
    <App />
  </BrowserRouter>
)
