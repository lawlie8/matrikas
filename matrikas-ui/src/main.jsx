import { createRoot } from 'react-dom/client'
import './index.css'
import { BrowserRouter, Router } from 'react-router'
import App from './App.jsx'

createRoot(document.getElementById('root')).render(
  <BrowserRouter>
    <App />
  </BrowserRouter>
)
