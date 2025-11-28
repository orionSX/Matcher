import './index.css'
import { BrowserRouter, Routes, Route, Link } from 'react-router-dom';
import Globe1 from './components/mvpblocks/globe1';
import LoginForm2 from './components/mvpblocks/login-form-2';
import LoginForm1 from './components/mvpblocks/login-form1';
import GradientHero from './components/mvpblocks/gradient-hero';
import ProfilePage from './pages/Profile';
import Header1 from './components/mvpblocks/header-1';

function App() {
  return (
    <BrowserRouter>
      <div className="min-h-screen bg-background text-foreground">
        <Header1/>
       
        <main>
          <Routes>
            <Route path="/" element={<Globe1 />} />
            <Route path="/lg" element={<LoginForm2 />} />
            <Route path="/hr" element={<GradientHero />} />
            <Route path="/profile" element={<ProfilePage />} />
          </Routes>
        </main>
      </div>
    </BrowserRouter>
  );
}

export default App
