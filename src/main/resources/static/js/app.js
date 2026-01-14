// API Endpoints
const API_BASE = '/api';
const DRIVERS_ENDPOINT = `${API_BASE}/drivers`;
const TEAMS_ENDPOINT = `${API_BASE}/teams`;

// DOM Elements
let driversTableBody, teamsContainer, driverForm, teamForm, driverModal, teamModal, toastEl, toast;

// State
let teams = [];
let drivers = [];
let currentTab = 'drivers';

// Initialize the application
document.addEventListener('DOMContentLoaded', () => {
    // Initialize DOM elements
    initializeElements();
    
    // Set up event listeners
    setupEventListeners();
    
    // Load initial data
    loadInitialData();
});

function initializeElements() {
    driversTableBody = document.getElementById('driversTableBody');
    teamsContainer = document.getElementById('teamsContainer');
    driverForm = document.getElementById('driverForm');
    teamForm = document.getElementById('teamForm');
    
    // Initialize modals if they exist
    const driverModalEl = document.getElementById('driverModal');
    const teamModalEl = document.getElementById('teamModal');
    const toastEl = document.getElementById('toast');
    
    if (driverModalEl) {
        driverModal = new bootstrap.Modal(driverModalEl);
    }
    
    if (teamModalEl) {
        teamModal = new bootstrap.Modal(teamModalEl);
    }
    
    if (toastEl) {
        toast = new bootstrap.Toast(toastEl, { delay: 3000 });
    }
}

function setupEventListeners() {
    // Tab switching
    const driversTab = document.getElementById('drivers-tab');
    const teamsTab = document.getElementById('teams-tab');
    
    if (driversTab) {
        driversTab.addEventListener('click', () => showSection('drivers'));
    }
    
    if (teamsTab) {
        teamsTab.addEventListener('click', () => showSection('teams'));
    }
    
    // Save buttons
    const saveDriverBtn = document.getElementById('saveDriverBtn');
    const saveTeamBtn = document.getElementById('saveTeamBtn');
    
    if (saveDriverBtn) {
        saveDriverBtn.addEventListener('click', saveDriver);
    }
    
    if (saveTeamBtn) {
        saveTeamBtn.addEventListener('click', saveTeam);
    }
    
    // Modal close events
    const driverModalEl = document.getElementById('driverModal');
    const teamModalEl = document.getElementById('teamModal');
    
    if (driverModalEl) {
        driverModalEl.addEventListener('hidden.bs.modal', () => {
            if (driverForm) driverForm.reset();
            document.getElementById('driverModalTitle').textContent = 'Add New Driver';
            const driverId = document.getElementById('driverId');
            if (driverId) driverId.value = '';
        });
    }
    
    if (teamModalEl) {
        teamModalEl.addEventListener('hidden.bs.modal', () => {
            if (teamForm) teamForm.reset();
            document.getElementById('teamModalTitle').textContent = 'Add New Team';
            const teamId = document.getElementById('teamId');
            if (teamId) teamId.value = '';
        });
    }
}

async function loadInitialData() {
    try {
        await Promise.all([loadDrivers(), loadTeams()]);

        renderDrivers();

        showSection(currentTab);
    } catch (error) {
        console.error('Error loading initial data:', error);
        showToast('Error', 'Failed to load initial data', 'danger');
    }
}

// Show toast notification
function showToast(title, message, type = 'primary') {
    const toastTitle = document.getElementById('toastTitle');
    const toastMessage = document.getElementById('toastMessage');
    const toast = document.querySelector('.toast');
    
    if (toastTitle) toastTitle.textContent = title;
    if (toastMessage) toastMessage.textContent = message;
    if (toast) {
        toast.className = `toast show bg-${type} text-white`;
        const bsToast = new bootstrap.Toast(toast);
        bsToast.show();
    }
}

// Show section
function showSection(section) {
    currentTab = section;
    const driversSection = document.getElementById('drivers');
    const teamsSection = document.getElementById('teams');
    const driversTab = document.getElementById('drivers-tab');
    const teamsTab = document.getElementById('teams-tab');
    
    if (section === 'drivers') {
        if (driversSection) driversSection.classList.add('show', 'active');
        if (teamsSection) teamsSection.classList.remove('show', 'active');
        if (driversTab) driversTab.classList.add('active');
        if (teamsTab) teamsTab.classList.remove('active');
    } else {
        if (teamsSection) teamsSection.classList.add('show', 'active');
        if (driversSection) driversSection.classList.remove('show', 'active');
        if (teamsTab) teamsTab.classList.add('active');
        if (driversTab) driversTab.classList.remove('active');
    }
}

// Load drivers from API
async function loadDrivers() {
    try {
        showLoading('drivers');
        const response = await fetch(DRIVERS_ENDPOINT);
        if (!response.ok) throw new Error('Failed to load drivers');
        
        drivers = await response.json();
        renderDrivers();
    } catch (error) {
        console.error('Error loading drivers:', error);
        showToast('Error', 'Failed to load drivers', 'danger');
        throw error;
    }
}


function renderDrivers() {
    if (!driversTableBody) return;

    if (!drivers || drivers.length === 0) {
        driversTableBody.innerHTML = `
            <tr>
                <td colspan="5" class="text-center py-4 text-muted">
                    No drivers found. Add your first driver!
                </td>
            </tr>`;
        return;
    }

    driversTableBody.innerHTML = drivers.map(driver => {
        const team = teams.find(t => t.id === driver.teamId);
        const teamName = team ? team.name : '—';

        return `
        <tr>
            <td>${driver.id || ''}</td>
            <td>${driver.name || ''}</td>
            <td>${driver.number || '—'}</td>
            <td>${teamName}</td> <td class="text-end">
                <button class="btn btn-sm btn-outline-primary me-1" onclick="editDriver(${driver.id})">
                    <i class="bi bi-pencil"></i>
                </button>
                <button class="btn btn-sm btn-outline-danger" onclick="deleteDriver(${driver.id})">
                    <i class="bi bi-trash"></i>
                </button>
            </td>
        </tr>
    `}).join('');
}

// Load teams from API
async function loadTeams() {
    try {
        showLoading('teams');
        const response = await fetch(TEAMS_ENDPOINT);
        if (!response.ok) throw new Error('Failed to load teams');
        
        teams = await response.json();
        renderTeams();
        populateTeamSelect();
    } catch (error) {
        console.error('Error loading teams:', error);
        showToast('Error', 'Failed to load teams', 'danger');
        throw error;
    }
}

// Render teams in the grid
function renderTeams() {
    if (!teamsContainer) return;
    
    if (!teams || teams.length === 0) {
        teamsContainer.innerHTML = `
            <div class="col-12 text-center py-4 text-muted">
                No teams found. Add your first team!
            </div>`;
        return;
    }

    teamsContainer.innerHTML = teams.map(team => `
        <div class="col-md-6 col-lg-4">
            <div class="card team-card h-100">
                <div class="card-header d-flex justify-content-between align-items-center">
                    <h5 class="mb-0">${team.name}</h5>
                    <div>
                        <button class="btn btn-sm btn-outline-primary me-1" onclick="editTeam(${team.id})">
                            <i class="bi bi-pencil"></i>
                        </button>
                        <button class="btn btn-sm btn-outline-danger" onclick="deleteTeam(${team.id})">
                            <i class="bi bi-trash"></i>
                        </button>
                    </div>
                </div>
                <div class="card-body">
                    <h6>Drivers</h6>
                    ${team.drivers && team.drivers.length > 0 
                        ? `<ul class="list-group list-group-flush">
                            ${team.drivers.map(driver => `
                                <li class="list-group-item d-flex justify-content-between align-items-center">
                                    ${driver.name}
                                    <span class="badge bg-primary rounded-pill">#${driver.number || '—'}</span>
                                </li>
                            `).join('')}
                           </ul>`
                        : '<p class="text-muted mb-0">No drivers in this team</p>'
                    }
                </div>
            </div>
        </div>
    `).join('');
}

// Populate team select in driver form
function populateTeamSelect() {
    const select = document.getElementById('driverTeam');
    if (!select) return;
    
    select.innerHTML = '<option value="">Select a team</option>';
    
    if (teams && teams.length > 0) {
        teams.forEach(team => {
            const option = document.createElement('option');
            option.value = team.id;
            option.textContent = team.name;
            select.appendChild(option);
        });
    }
}

// Show loading state
function showLoading(section) {
    if (section === 'drivers' && driversTableBody) {
        driversTableBody.innerHTML = `
            <tr>
                <td colspan="5" class="text-center py-4">
                    <div class="d-flex justify-content-center align-items-center">
                        <div class="spinner-border text-primary me-2" role="status">
                            <span class="visually-hidden">Loading...</span>
                        </div>
                        <span>Loading drivers...</span>
                    </div>
                </td>
            </tr>`;
    } else if (section === 'teams' && teamsContainer) {
        teamsContainer.innerHTML = `
            <div class="col-12 text-center py-4">
                <div class="d-flex justify-content-center align-items-center">
                    <div class="spinner-border text-primary me-2" role="status">
                        <span class="visually-hidden">Loading...</span>
                    </div>
                    <span>Loading teams...</span>
                </div>
            </div>`;
    }
}

// Save or update driver
async function saveDriver() {
    const driverId = document.getElementById('driverId')?.value;
    const name = document.getElementById('driverName')?.value?.trim();
    const number = parseInt(document.getElementById('driverNumber')?.value) || null;
    const teamId = parseInt(document.getElementById('driverTeam')?.value) || null;

    if (!name || !teamId) {
        showToast('Validation Error', 'Please fill in all required fields', 'warning');
        return;
    }

    const driverData = {
            id: driverId ? parseInt(driverId) : null,
            name,
            number,
            teamId: teamId
        };

    try {
        const url = driverId ? `${DRIVERS_ENDPOINT}/update/${driverId}` : `${DRIVERS_ENDPOINT}/save`;
        const method = driverId ? 'PUT' : 'POST';

        const response = await fetch(url, {
            method,
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(driverData),
        });

        if (!response.ok) {
            const error = await response.text();
            throw new Error(error || 'Failed to save driver');
        }

        if (driverModal) driverModal.hide();
        await loadTeams();
        await loadDrivers();
        showToast('Success', `Driver ${driverId ? 'updated' : 'added'} successfully!`, 'success');
    } catch (error) {
        console.error('Error saving driver:', error);
        showToast('Error', error.message || 'Failed to save driver', 'danger');
    }
}

// Save or update team
async function saveTeam() {
    const teamId = document.getElementById('teamId')?.value;
    const name = document.getElementById('teamName')?.value?.trim();

    if (!name) {
        showToast('Validation Error', 'Please enter a team name', 'warning');
        return;
    }

    const teamData = {
        id: teamId ? parseInt(teamId) : null,
        name
    };

    try {
        const url = teamId ? `${TEAMS_ENDPOINT}/update/${teamId}` : `${TEAMS_ENDPOINT}/save`;
        const method = teamId ? 'PUT' : 'POST';

        const response = await fetch(url, {
            method,
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(teamData),
        });

        if (!response.ok) {
            const error = await response.text();
            throw new Error(error || 'Failed to save team');
        }

        if (teamModal) teamModal.hide();
        await loadTeams();
        showToast('Success', `Team ${teamId ? 'updated' : 'added'} successfully!`, 'success');
    } catch (error) {
        console.error('Error saving team:', error);
        showToast('Error', error.message || 'Failed to save team', 'danger');
    }
}

// Make functions available globally
window.editDriver = async (id) => {
    try {
        const response = await fetch(`${DRIVERS_ENDPOINT}/${id}`);
        if (!response.ok) throw new Error('Failed to load driver');
        
        const driver = await response.json();
        
        document.getElementById('driverId').value = driver.id;
        document.getElementById('driverName').value = driver.name || '';
        document.getElementById('driverNumber').value = driver.number || '';
            document.getElementById('driverTeam').value = driver.teamId || '';
        
        document.getElementById('driverModalTitle').textContent = 'Edit Driver';
        if (driverModal) driverModal.show();
    } catch (error) {
        console.error('Error loading driver:', error);
        showToast('Error', 'Failed to load driver details', 'danger');
    }
};

window.deleteDriver = async (id) => {
    if (!confirm('Are you sure you want to delete this driver?')) return;
    
    try {
        // First get the driver to get the name for deletion
        const driverResponse = await fetch(`${DRIVERS_ENDPOINT}/${id}`);
        if (!driverResponse.ok) throw new Error('Failed to load driver for deletion');
        
        const driver = await driverResponse.json();
        
        // Then delete by name as per your API
        const response = await fetch(`${DRIVERS_ENDPOINT}/deleteByName?name=${encodeURIComponent(driver.name)}`, {
            method: 'DELETE'
        });
        
        if (!response.ok) {
            const error = await response.text();
            throw new Error(error || 'Failed to delete driver');
        }
        
        await loadDrivers();
        await loadTeams();
        showToast('Success', 'Driver deleted successfully!', 'success');
    } catch (error) {
        console.error('Error deleting driver:', error);
        showToast('Error', error.message || 'Failed to delete driver', 'danger');
    }
};

window.editTeam = async (id) => {
    try {
        const response = await fetch(`${TEAMS_ENDPOINT}/${id}`);
        if (!response.ok) throw new Error('Failed to load team');
        
        const team = await response.json();
        
        document.getElementById('teamId').value = team.id;
        document.getElementById('teamName').value = team.name || '';
        
        document.getElementById('teamModalTitle').textContent = 'Edit Team';
        if (teamModal) teamModal.show();
    } catch (error) {
        console.error('Error loading team:', error);
        showToast('Error', 'Failed to load team details', 'danger');
    }
};

window.deleteTeam = async (id) => {
    if (!confirm('Are you sure you want to delete this team? This will remove all associated drivers.')) return;
    
    try {
        // First get the team to get the name for deletion
        const teamResponse = await fetch(`${TEAMS_ENDPOINT}/${id}`);
        if (!teamResponse.ok) throw new Error('Failed to load team for deletion');
        
        const team = await teamResponse.json();
        
        // Then delete by name as per your API
        const response = await fetch(`${TEAMS_ENDPOINT}/deleteByName?name=${encodeURIComponent(team.name)}`, {
            method: 'DELETE'
        });
        
        if (!response.ok) {
            const error = await response.text();
            throw new Error(error || 'Failed to delete team');
        }
        
        await loadTeams();
        await loadDrivers();
        showToast('Success', 'Team deleted successfully!', 'success');
    } catch (error) {
        console.error('Error deleting team:', error);
        showToast('Error', error.message || 'Failed to delete team', 'danger');
    }
};