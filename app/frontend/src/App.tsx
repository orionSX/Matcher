import './index.css'
import { BrowserRouter, Routes, Route, Link } from 'react-router-dom';
import Globe1 from './components/mvpblocks/globe1';
import LoginForm2 from './components/mvpblocks/login-form-2';
import LoginForm1 from './components/mvpblocks/login-form1';
import GradientHero from './components/mvpblocks/gradient-hero';
import ProfilePage from './pages/Profile';
import Matches from './pages/Matches';
import CreateMatch from './pages/CreateMatch';
import Header1 from './components/mvpblocks/header-1';
import MultiStepForm from './macro-components/multi-step-form';
import FooterGlow from './components/mvpblocks/footer-glow';
import { Toaster } from '@/components/ui/sonner';

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
            <Route path="/qwer" element={<MultiStepForm />} />
            <Route path="/matches" element={<Matches />} />
            <Route path="/matches/create" element={<CreateMatch />} />
          </Routes>
        </main>
        <FooterGlow/>
        <Toaster position="top-right" richColors />
      </div>
    </BrowserRouter>
  );
}

export default App
