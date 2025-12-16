'use client'
import { useState } from 'react'
import { Link, useNavigate } from 'react-router-dom'
import { useTranslation } from 'react-i18next'
import { Button } from '@/components/ui/button'
import { Input } from '@/components/ui/input'
import { Label } from '@/components/ui/label'
import { Card, CardContent, CardDescription, CardFooter, CardHeader, CardTitle } from '@/components/ui/card'
import { Eye, EyeOff } from 'lucide-react'

export default function Login() {
  const { t } = useTranslation()
  const navigate = useNavigate()
  const [showPassword, setShowPassword] = useState(false)
  const [email, setEmail] = useState('')
  const [password, setPassword] = useState('')

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault()
    // Dummy login - в реальном приложении здесь будет API вызов
    console.log('Login:', { email, password })
    navigate('/profile')
  }

  return (
    <div className="flex min-h-screen items-center justify-center bg-gradient-to-br from-black via-rose-950/20 to-black px-4 pt-20">
      <div className="w-full max-w-md">
        {/* Logo/Title */}
        <div className="mb-8 text-center">
          <h1 className="mb-2 bg-gradient-to-r from-rose-500 to-rose-700 bg-clip-text text-4xl font-bold text-transparent">
            AkioraGG
          </h1>
          <p className="text-muted-foreground">{t('auth.login.subtitle')}</p>
        </div>

        <Card className="border-rose-900/50 bg-black/40 backdrop-blur">
          <CardHeader>
            <CardTitle className="text-2xl text-rose-50">{t('auth.login.title')}</CardTitle>
            <CardDescription className="text-muted-foreground">
              {t('auth.login.subtitle')}
            </CardDescription>
          </CardHeader>
          <CardContent>
            <form onSubmit={handleSubmit} className="space-y-4">
              <div className="space-y-2">
                <Label htmlFor="email" className="text-rose-50">
                  {t('auth.login.email')}
                </Label>
                <Input
                  id="email"
                  type="email"
                  placeholder="name@example.com"
                  value={email}
                  onChange={(e) => setEmail(e.target.value)}
                  required
                  className="border-rose-900/50 bg-black/40 text-rose-50 placeholder:text-muted-foreground focus-visible:border-rose-500 focus-visible:ring-rose-500/50"
                />
              </div>

              <div className="space-y-2">
                <div className="flex items-center justify-between">
                  <Label htmlFor="password" className="text-rose-50">
                    {t('auth.login.password')}
                  </Label>
                  <Link
                    to="/forgot-password"
                    className="text-sm text-rose-400 hover:text-rose-300 transition-colors"
                  >
                    {t('auth.login.forgotPassword')}
                  </Link>
                </div>
                <div className="relative">
                  <Input
                    id="password"
                    type={showPassword ? 'text' : 'password'}
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                    required
                    className="border-rose-900/50 bg-black/40 pr-10 text-rose-50 placeholder:text-muted-foreground focus-visible:border-rose-500 focus-visible:ring-rose-500/50"
                  />
                  <button
                    type="button"
                    onClick={() => setShowPassword(!showPassword)}
                    className="absolute right-3 top-1/2 -translate-y-1/2 text-muted-foreground hover:text-rose-400 transition-colors"
                  >
                    {showPassword ? <EyeOff size={18} /> : <Eye size={18} />}
                  </button>
                </div>
              </div>

              <Button
                type="submit"
                className="w-full bg-gradient-to-r from-rose-600 to-rose-700 hover:from-rose-500 hover:to-rose-600 text-white font-semibold"
              >
                {t('auth.login.submit')}
              </Button>
            </form>
          </CardContent>
          <CardFooter className="flex-col space-y-2">
            <div className="text-center text-sm text-muted-foreground">
              {t('auth.login.noAccount')}{' '}
              <Link to="/register" className="text-rose-400 hover:text-rose-300 font-medium transition-colors">
                {t('auth.login.signUp')}
              </Link>
            </div>
          </CardFooter>
        </Card>

        {/* Decorative glow */}
        <div className="absolute inset-0 -z-10 overflow-hidden pointer-events-none">
          <div className="absolute top-1/4 left-1/4 h-96 w-96 rounded-full bg-rose-600/20 blur-3xl" />
          <div className="absolute bottom-1/4 right-1/4 h-96 w-96 rounded-full bg-rose-800/20 blur-3xl" />
        </div>
      </div>
    </div>
  )
}
