const API = "http://localhost:8080";

const loginPage = document.getElementById("login-page");
const dashboardPage = document.getElementById("dashboard-page");
const loginForm = document.getElementById("login-form");
const loginError = document.getElementById("login-error");
const logoutBtn = document.getElementById("logout-btn");

const topbarUsername = document.getElementById("topbar-username");
const pUsername = document.getElementById("p-username");
const pEmail = document.getElementById("p-email");
const pRoles = document.getElementById("p-roles");

const usersTable = document.getElementById("users-table");
const rolesTable = document.getElementById("roles-table");

const menuItems = document.querySelectorAll(".menu-item");

function showPage(pageId) {
    document.querySelectorAll(".section").forEach(s => s.classList.remove("active"));
    document.getElementById(pageId + "-section").classList.add("active");
}

menuItems.forEach(item => {
    item.addEventListener("click", () => {
        showPage(item.dataset.section);
    });
});

function saveToken(token) {
    localStorage.setItem("jwt", token);
}

function getToken() {
    return localStorage.getItem("jwt");
}

async function api(path, method = "GET", body = null) {
    const headers = { "Content-Type": "application/json" };
    if (getToken()) headers["Authorization"] = "Bearer " + getToken();

    return fetch(API + path, {
        method,
        headers,
        body: body ? JSON.stringify(body) : null
    });
}

/* LOGIN */
loginForm.addEventListener("submit", async (e) => {
    e.preventDefault();

    const username = document.getElementById("login-username").value;
    const password = document.getElementById("login-password").value;

    const res = await api("/api/auth/login", "POST", { userName: username, password });

    if (!res.ok) {
        loginError.textContent = "Xəta: Yanlış məlumat.";
        loginError.classList.remove("hidden");
        return;
    }

    const data = await res.json();
    saveToken(data.token);

    loadDashboard();
});

/* LOAD DASHBOARD */
async function loadDashboard() {
    loginPage.classList.remove("active");
    dashboardPage.classList.add("active");

    // get all users
    const res = await api("/api/v1/users/users");
    const users = await res.json();

    const decoded = parseJwt(getToken());
    const currentUser = users.find(u => u.username === decoded.sub);

    topbarUsername.textContent = currentUser.username;
    pUsername.textContent = currentUser.username;
    pEmail.textContent = currentUser.email;
    pRoles.textContent = currentUser.roles.join(", ");

    // role-based UI
    if (!currentUser.roles.includes("ADMIN") && !currentUser.roles.includes("SUPER_ADMIN")) {
        document.querySelector(".admin-only").remove();
    }
    if (!currentUser.roles.includes("SUPER_ADMIN")) {
        document.querySelector(".superadmin-only").remove();
    }

    renderUsers(users);
    loadRoles();
}

function renderUsers(users) {
    usersTable.innerHTML = "";
    users.forEach(u => {
        usersTable.innerHTML += `
            <tr>
                <td>${u.username}</td>
                <td>${u.email}</td>
                <td>${u.roles.join(", ")}</td>
            </tr>`;
    });
}

async function loadRoles() {
    const res = await api("/api/v1/roles/getAllRoles");
    if (!res.ok) return;

    const roles = await res.json();
    rolesTable.innerHTML = "";
    roles.forEach(r => {
        rolesTable.innerHTML += `
            <tr>
                <td>${r.id}</td>
                <td>${r.name}</td>
                <td>${r.description}</td>
            </tr>
        `;
    });
}

logoutBtn.onclick = () => {
    localStorage.removeItem("jwt");
    location.reload();
};

/* Helper: decode JWT */
function parseJwt(token) {
    return JSON.parse(atob(token.split(".")[1]));
}

/* Auto login on reload */
if (getToken()) loadDashboard();
