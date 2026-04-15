**Spring Security System (JWT + OAuth2 + RBAC + Caching)**
A production-ready Spring Boot security system implementing JWT authentication, OAuth2 login (Google), role-based access control and caching.

**Features**
🔐 JWT Authentication (Stateless Security)
🌐 OAuth2 Login (Google)
👥 Role-Based Access Control (RBAC)
🔑 Permission-Based Authorization
⚡ Spring Cache (Performance Optimization)
🧠 Method-Level Security (@PreAuthorize, @PostAuthorize)
🔄 Auto User Creation (OAuth2)

**System Architecture**
                ┌──────────────┐
                │   FRONTEND   │
                └──────┬───────┘
                       │
        ┌──────────────┼──────────────┐
        │                             │
  Email/Password               Google OAuth2
        │                             │
POST /authenticate        /oauth2/authorization/google
        │                             │
        ▼                             ▼
 AuthenticationManager           OAuth2User
        │                             │
        └──────────────┬──────────────┘
                       ▼
                 Generate JWT
                       ▼
              Return Token to Client
                       ▼
        Authorization: Bearer <JWT>
                       ▼
                Secure APIs Access


**Authentication Flow**

1️⃣ JWT Login
POST /authenticate → JWT Token → Access APIs

**👉 Controller:**

2️⃣ OAuth2 Login (Google)
/oauth2/authorization/google
        ↓
Google Login
        ↓
Spring Security Callback
        ↓
Save User in DB
        ↓
Generate JWT


**🔑 Authorization Model**
Roles & Permissions
Role	Permissions
ADMIN	READ, WRITE, DELETE
USER	READ

**🌦️ API Endpoints**
🔐 Authentication
Method	Endpoint	Description
POST	/authenticate	Login & get JWT
POST	/api/users/register	Register user
GET	/oauth2/authorization/google	Google login
🌦️ Weather APIs
Method	Endpoint	Access
GET	/weather?city=Delhi	WEATHER_READ
POST	/weather	WEATHER_WRITE
GET	/weather/all	ADMIN + USER
DELETE	/weather/{city}	Authenticated


📊 Logs API
Method	Endpoint	Description
GET	/logs/{id}	Fetch log
POST	/logs/create	Create log


🔄 JWT Flow
Client → Login → JWT Generated
       ↓
Store Token (Frontend)
       ↓
Send in Header
       ↓
JwtAuthFilter validates
       ↓
Access granted


****🗄️ Database Design****
**Users Table**
id
username
password
role


**Weather Table**
city
forecast


**Weather Logs**
action
performedBy
timestamp

**⚙️ Setup Guide**
1️⃣ Clone Repo
git clone https://github.com/ashu12355/Spring-Security.git
2️⃣ Configure OAuth2
spring.security.oauth2.client.registration.google.client-id=YOUR_CLIENT_ID
spring.security.oauth2.client.registration.google.client-secret=YOUR_SECRET
spring.security.oauth2.client.registration.google.scope=openid,profile,email
3️⃣ Add Redirect URI (Google Console)
http://localhost:8080/login/oauth2/code/google
4️⃣ Run Project
mvn spring-boot:run
🧪 Testing
JWT Login (Postman)
POST /authenticate
{
  "username": "admin",
  "password": "123456"
}
OAuth2 Login

Open browser:

http://localhost:8080/oauth2/authorization/google
🚀 Future Enhancements
🔁 Refresh Token System
🚪 Logout Mechanism
⚡ Redis Caching
🌐 Multi OAuth Providers (GitHub, Facebook)
📈 Rate Limiting
👨‍💻 Author

Ashutosh Sharma
🔗 GitHub: https://github.com/ashu12355
🔗 LinkedIn: www.linkedin.com/in/ashu12355
