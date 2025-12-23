// API Configuration
const API_BASE_URL = import.meta.env.VITE_SEARCH_FORM_SERVICE_URL || 'http://localhost:5004/api/v1';
const USER_SERVICE_URL = import.meta.env.VITE_USER_SERVICE_URL || 'http://localhost:5002/api';
const NOTIFICATION_SERVICE_URL = import.meta.env.VITE_NOTIFICATION_SERVICE_URL || 'http://localhost:5003/api';

// Types
export interface LoginRequest {
  email: string;
  password: string;
}

export interface RegisterRequest {
  nickname: string;
  email: string;
  password: string;
  age?: number;
  gender?: string;
  socials?: { platform: string; url: string }[];
}

export interface User {
  userId: string;
  nickname: string;
  email: string;
  age?: number;
  gender?: string;
  socials?: { platform: string; url: string }[];
  createdAt?: string;
}

export interface LeagueAccount {
  nickname: string;
  server: string;
  tag: string;
}

export interface RankedStats {
  current_rank: string;
  current_lp: number | null;
  best_rank: string | null;
  best_lp: number | null;
  win_rate: string | null;
  win_loss: string | null;
  icon_id: number;
}

export interface ChampionStats {
  position: string;
  champion: string;
  wins: string;
  losses: string;
  win_rate: string;
  kda: string;
  cs?: string;
  cs_per_minute?: string;
  gold?: string;
  gold_per_minute?: string;
  damage_per_minute?: string;
  damage_share_ratio?: string;
  laning?: string;
  wards_score?: string;
  wards_control?: string;
  double_kills?: string;
  triple_kills?: string;
  quadra_kills?: string;
  penta_kills?: string;
}

export interface AccountInfo {
  solo_queue?: RankedStats;
  flex_queue?: RankedStats;
  champion_stats?: ChampionStats[];
}

export interface LeaguePreferences {
  my_roles: string[];
  teammate_roles: string[];
  looking_for_ranks: Array<{ rank: string; tier: number }>;
  mode: string[];
  server: string[];
  smurf_only: boolean;
}

export interface PersonPreferences {
  min_age?: number;
  max_age?: number;
  gender?: string;
  voice?: boolean;
}

export interface CreateFormRequest {
  creator_id: string;
  account: LeagueAccount;
  league_preferences: LeaguePreferences;
  person_preferences: PersonPreferences;
  description: string;
}

export interface HotP2PForm {
  id: string;
  creator_id: string;
  account: LeagueAccount;
  account_info?: AccountInfo;
  league_preferences: LeaguePreferences;
  person_preferences: PersonPreferences;
  user_data?: {
    nickname: string;
    email: string;
    age?: number;
    gender?: string;
    socials?: string[];
  };
  description?: string;
  created_at: string;
  expires_at: string;
  liked_by: string[];
  disliked_by: string[];
}

export interface UpdateUserRequest {
  nickname?: string;
  email?: string;
  age?: number;
  gender?: string;
  socials?: { platform: string; url: string }[];
}

// Auth API
export const authAPI = {
  async login(data: LoginRequest): Promise<User> {
    const response = await fetch(`${USER_SERVICE_URL}/auth/login`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(data),
    });
    if (!response.ok) {
      const error = await response.text();
      throw new Error(error || 'Login failed');
    }
    return response.json();
  },

  async register(data: RegisterRequest): Promise<User> {
    const response = await fetch(`${USER_SERVICE_URL}/auth/register`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(data),
    });
    if (!response.ok) {
      const error = await response.text();
      throw new Error(error || 'Registration failed');
    }
    return response.json();
  },
};

// User API
export const userAPI = {
  async getUser(userId: string): Promise<User> {
    const response = await fetch(`${USER_SERVICE_URL}/users/${userId}`);
    if (!response.ok) throw new Error('Failed to fetch user');
    return response.json();
  },

  async updateUser(userId: string, data: UpdateUserRequest): Promise<User> {
    const response = await fetch(`${USER_SERVICE_URL}/users/${userId}`, {
      method: 'PATCH',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(data),
    });
    if (!response.ok) throw new Error('Failed to update user');
    return response.json();
  },

  async updateNickname(userId: string, nickname: string): Promise<User> {
    const response = await fetch(`${USER_SERVICE_URL}/users/${userId}/nickname`, {
      method: 'PATCH',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ nickname }),
    });
    if (!response.ok) throw new Error('Failed to update nickname');
    return response.json();
  },

  async updateEmail(userId: string, email: string): Promise<User> {
    const response = await fetch(`${USER_SERVICE_URL}/users/${userId}/email`, {
      method: 'PATCH',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ email }),
    });
    if (!response.ok) throw new Error('Failed to update email');
    return response.json();
  },

  async updateAge(userId: string, age: number): Promise<User> {
    const response = await fetch(`${USER_SERVICE_URL}/users/${userId}/age`, {
      method: 'PATCH',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ age }),
    });
    if (!response.ok) throw new Error('Failed to update age');
    return response.json();
  },

  async updateGender(userId: string, gender: string): Promise<User> {
    const response = await fetch(`${USER_SERVICE_URL}/users/${userId}/gender`, {
      method: 'PATCH',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ gender }),
    });
    if (!response.ok) throw new Error('Failed to update gender');
    return response.json();
  },

  async updateSocials(userId: string, socialsMap: Record<string, { platform: string; url: string }>): Promise<User> {
    const response = await fetch(`${USER_SERVICE_URL}/users/${userId}/socials`, {
      method: 'PATCH',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ socials: socialsMap }),
    });
    if (!response.ok) throw new Error('Failed to update socials');
    return response.json();
  },
};

// Forms API
export const formsAPI = {
  async createForm(data: CreateFormRequest): Promise<HotP2PForm> {
    const response = await fetch(`${API_BASE_URL}/forms`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(data),
    });
    if (!response.ok) {
      const error = await response.text();
      throw new Error(error || 'Failed to create form');
    }
    return response.json();
  },

  async getAllForms(userId?: string): Promise<HotP2PForm[]> {
    const url = userId 
      ? `${API_BASE_URL}/forms?userId=${userId}`
      : `${API_BASE_URL}/forms`;
    const response = await fetch(url);
    if (!response.ok) throw new Error('Failed to fetch forms');
    return response.json();
  },

  async getFormsByCreator(creatorId: string): Promise<HotP2PForm[]> {
    const response = await fetch(`${API_BASE_URL}/forms/by-creator/${creatorId}`);
    if (!response.ok) throw new Error('Failed to fetch forms');
    return response.json();
  },

  async getForm(formId: string): Promise<HotP2PForm> {
    const response = await fetch(`${API_BASE_URL}/forms/${formId}`);
    if (!response.ok) throw new Error('Failed to fetch form');
    return response.json();
  },

  async likeForm(formId: string, userId: string): Promise<HotP2PForm> {
    const response = await fetch(`${API_BASE_URL}/forms/like`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({"form_id":formId, "user_id":userId}),
    });
    if (!response.ok) throw new Error('Failed to like form');
    return response.json();
  },

  async dislikeForm(formId: string, userId: string): Promise<HotP2PForm> {
    console.log(JSON.stringify({ formId, userId }))
    const response = await fetch(`${API_BASE_URL}/forms/dislike`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({"form_id":formId, "user_id":userId}),
    });
    if (!response.ok) throw new Error('Failed to dislike form');
    return response.json();
  },

  async getLikedForms(userId: string): Promise<HotP2PForm[]> {
    const response = await fetch(`${API_BASE_URL}/forms/liked-by/${userId}`);
    if (!response.ok) throw new Error('Failed to fetch liked forms');
    return response.json();
  },

  async getDislikedForms(userId: string): Promise<HotP2PForm[]> {
    const response = await fetch(`${API_BASE_URL}/forms/disliked-by/${userId}`);
    if (!response.ok) throw new Error('Failed to fetch disliked forms');
    return response.json();
  },
};

// Notification API
export const notificationAPI = {
  async subscribeTelegram(userId: string): Promise<void> {
    const response = await fetch(`${NOTIFICATION_SERVICE_URL}/notifier-users/${userId}/telegram`, {
      method: 'PUT',
    });
    if (!response.ok) throw new Error('Failed to subscribe to Telegram notifications');
  },

  async unsubscribeTelegram(userId: string): Promise<void> {
    const response = await fetch(`${NOTIFICATION_SERVICE_URL}/notifier-users/${userId}/telegram-disable`, {
      method: 'PUT',
    });
    if (!response.ok) throw new Error('Failed to unsubscribe from Telegram notifications');
  },

  async subscribeEmail(userId: string): Promise<void> {
    const response = await fetch(`${NOTIFICATION_SERVICE_URL}/notifier-users/${userId}/email`, {
      method: 'PUT',
    });
    if (!response.ok) throw new Error('Failed to subscribe to Email notifications');
  },

  async unsubscribeEmail(userId: string): Promise<void> {
    const response = await fetch(`${NOTIFICATION_SERVICE_URL}/notifier-users/${userId}/email-disable`, {
      method: 'PUT',
    });
    if (!response.ok) throw new Error('Failed to unsubscribe from Email notifications');
  },
};
