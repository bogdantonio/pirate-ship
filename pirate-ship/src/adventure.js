// Map Role Name -> The 3 keys used in 'roleData'
// note: keys must match what your Java server sends (snake_case)
const roleSkillMap = {
    "SECOND":       ["leadership", "tactics", "morale_boost"],
    "NAVIGATOR":    ["navigation", "weather_prediction", "map_reading"],
    "SNIPER":       ["accuracy", "weapon_rage", "critical_chance"],
    "COOK":         ["cooking", "meal_quality", "morale_impact"],
    "ARCHEOLOGIST": ["artifact_knowledge", "digging", "trap_detection"],
    "DOCTOR":       ["medical_ability", "healing_speed", "diagnosis"],
    "SHIPWRIGHT":   ["repair", "construction", "materials"],
    "MUSICIAN":     ["music", "inspiration", "buff_strength"],
    "HELMSMAN":     ["maneuvering", "precision", "storm_riding"]
};

// Helper to make "weather_prediction" look like "Weather Prediction"
function formatStatName(key) {
    return key.replace(/_/g, ' ').replace(/\b\w/g, l => l.toUpperCase());
}

let events = [];
let eventSetId = 0; // NEW: Store the ID of the adventure scenario
let currentEventIndex = 0;
let successCount = 0;
let failCount = 0;

// Load Crew Stats from Local Storage into a usable Map
const crewMap = {};

function loadCrewData() {
    const roles = ["second", "navigator", "sniper", "cook", "archeologist", "doctor", "shipwright", "musician", "helmsman"];
    roles.forEach(r => {
        const json = localStorage.getItem(`selected_${r}`);
        if(json) crewMap[r.toUpperCase()] = JSON.parse(json);
    });
}

window.onload = async () => {
    loadCrewData();
    const area = document.getElementById('leftPanel');
    area.innerHTML = "<h2 style='color:white'>Charting the course...</h2>";

    try {
        const response = await fetch('http://localhost:8080/api/adventure/start');
        const data = await response.json(); // UPDATE: Expect an object, not just an array

        // Extract the Set ID and the Events list
        eventSetId = data.eventSetId;
        events = data.events;

        if(events && events.length > 0) {
            renderEvent(0);
        } else {
            alert("No events found!");
        }
    } catch (e) {
        console.error(e);
        area.innerHTML = "<h2 style='color:red'>Server Connection Failed</h2>";
    }
};

function renderEvent(index) {
    if(index >= events.length) {
        showEndGame();
        return;
    }

    const event = events[index];
    const leftPanel = document.getElementById('leftPanel');
    const rightPanel = document.getElementById('rightPanel');

    // Hide Overlay if open
    document.getElementById('resultOverlay').style.display = 'none';

    if (event.type === "ENEMY") {
        renderEnemyEvent(event, leftPanel);
    } else {
        renderSubclassEvent(event, leftPanel);
    }
}

function getRandomImage(sex) {
    const randomNum = Math.floor(Math.random() * 5) + 1;
    if (sex === 'F') {
        return `img/enemy/fem${randomNum}.png`;
    } else {
        return `img/enemy/male${randomNum}.png`;
    }
}

// --- RENDER ENEMY ---
function renderEnemyEvent(event, left) {
    const imgPath = getRandomImage(event.enemySex);

    left.innerHTML = `
        <img src="${imgPath}" class="enemy-event-img"> <h2 style="color:#ff4444; font-family:'Pirata One'; font-size:2rem; margin:0;">${event.enemyName}</h2>
        <p style="color:#aaa; font-style:italic;">"${event.enemyAlias}"</p>
        <div style="text-align:left; width:100%; margin-top:10px; color:#e0e0e0;">
            <div><strong>Faction:</strong> ${event.enemyFaction}</div>
            <div><strong>Power:</strong> <span style="color:#ff4444">${event.enemyPower}</span></div>
        </div>
    `;

    const content = `
        <div id="activeContent">
            <p class="prompt-text">"${event.prompt}"</p>
            <button class="action-btn" onclick="handleEnemyFight()">FIGHT!</button>
        </div>
    `;
    updateRightPanel(content);
}

function getRandomClassImage(role) {
    const r = role.toUpperCase();
    switch (r){
        case "SECOND": return `img/class_icons/second.png`;
        case "NAVIGATOR": return `img/class_icons/navigator.png`;
        case "COOK": return `img/class_icons/cook.png`;
        case "SNIPER": return `img/class_icons/sniper.png`;
        case "ARCHEOLOGIST": return `img/class_icons/archeologist.png`;
        case "DOCTOR": return `img/class_icons/doctor.png`;
        case "SHIPWRIGHT": return `img/class_icons/shipwright.png`;
        case "MUSICIAN": return `img/class_icons/musician.png`;
        case "HELMSMAN": return `img/class_icons/helmsman.png`;
        default: return `img/class_icons/second.png`;
    }
}

// --- RENDER SUBCLASS ---
function renderSubclassEvent(event, left) {
    const roleName = event.subclassRole.toUpperCase();
    const crewMember = crewMap[roleName];

    let imgPath = getRandomClassImage(roleName);
    let charName = crewMember ? crewMember.name : "Unknown Pirate";

    const skills = roleSkillMap[roleName] || ["Skill 1", "Skill 2", "Skill 3"];

    left.innerHTML = `
        <img src="${imgPath}" class="class-icon-img">
        
        <h2 style="color:#ffd700; font-family:'Pirata One'; font-size:2rem; margin:0;">${roleName}</h2>
        <p style="color:#aaa;">${charName} steps forward...</p>
        
        <div class="req-stats-box">
            <div style="color:#ffd700; border-bottom:1px solid #5a1a1a; margin-bottom:5px;">Requirements (Pass 2):</div>
            <ul style="color:#e0e0e0; padding-left:20px; margin:0;">
                <li>${formatStatName(skills[0])} > ${event.req1}</li>
                <li>${formatStatName(skills[1])} > ${event.req2}</li>
                <li>${formatStatName(skills[2])} > ${event.req3}</li>
            </ul>
        </div>
    `;

    const content = `
        <div id="activeContent">
            <p class="prompt-text">"${event.prompt}"</p>
            <button class="action-btn" onclick="handleSubclassAction()">ATTEMPT</button>
        </div>
    `;
    updateRightPanel(content);
}

// --- LOGIC: HANDLE ACTIONS ---
function handleSubclassAction() {
    const event = events[currentEventIndex];
    const roleName = event.subclassRole.toUpperCase();
    const crewMember = crewMap[roleName];

    if (!crewMember || !crewMember.roleData) {
        console.error("Missing crew member data for", roleName);
        showResult(false, "The crew member is missing!");
        return;
    }

    const skills = roleSkillMap[roleName];
    const s1 = crewMember.roleData[skills[0]] || 0;
    const s2 = crewMember.roleData[skills[1]] || 0;
    const s3 = crewMember.roleData[skills[2]] || 0;

    console.log(`Checking ${roleName}: ${skills[0]}=${s1} vs ${event.req1}, ${skills[1]}=${s2} vs ${event.req2}, ...`);

    const passed = eventPassed(event.req1, event.req2, event.req3, s1, s2, s3);

    showResult(passed, passed ? `${crewMember.name}'s expertise proved sufficient!` : `${crewMember.name} wasn't skilled enough.`);
}

function handleEnemyFight() {
    const event = events[currentEventIndex];
    // Simple Logic: Crew Total Power vs Enemy Power
    // We grab the Captain's power (or you could store calculated Crew Power in localStorage)
    const crewPower = 50 + Math.floor(Math.random() * 50); // Simulation

    const passed = crewPower >= event.enemyPower;

    showResult(passed, passed ? `You defeated ${event.enemyName}!` : `You were overpowered by ${event.enemyName}!`);
}

function eventPassed(reqStat1, reqStat2, reqStat3, stat1, stat2, stat3) {
    if((reqStat1 < stat1 && reqStat2 < stat2)
        || (reqStat1 < stat1 && reqStat3 < stat3)
        || (reqStat3 < stat3 && reqStat2 < stat2))
    {
        return true;
    }
    return false;
}

// --- UI UPDATES ---

function updateRightPanel(html) {
    const old = document.getElementById('activeContent');
    if(old) old.remove();

    document.getElementById('rightPanel').insertAdjacentHTML('afterbegin', html);
}

function showResult(passed, message) {
    const overlay = document.getElementById('resultOverlay');
    const title = document.getElementById('resultTitle');
    const desc = document.getElementById('resultDesc');

    overlay.style.display = 'flex';

    if(passed) {
        successCount++;
        title.innerText = "SUCCESS";
        title.className = "result-title pass";
    } else {
        failCount++;
        title.innerText = "FAILURE";
        title.className = "result-title fail";
    }
    desc.innerText = message;
}

function nextEvent() {
    currentEventIndex++;
    renderEvent(currentEventIndex);
}

function showEndGame() {
    // 1. Send Results to Server (NEW)
    const payload = {
        crewId: localStorage.getItem("crewId"),
        eventSetId: eventSetId, // Now we have the correct ID
        success: successCount,
        fail: failCount
    };

    fetch('http://localhost:8080/api/adventure/end', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(payload)
    }).catch(err => console.error("Failed to save history:", err));

    const hasWon = successCount >= 7;

    const titleText = hasWon ? "TREASURE FOUND!" : "LOST AT SEA...";
    const color = hasWon ? "#ffd700" : "#ff4444";
    const message = hasWon
        ? "Your crew has conquered all the challenges. The treasure is yours!"
        : "The storms were too strong. Your story ends here in the depths.";

    document.body.innerHTML = `
        <div class="main-container">
            <h1 class="game-title" style="color: ${color}">${titleText}</h1>
            
            <div style="color:white; font-size:1.5rem; text-align:center; max-width:600px; margin-bottom:20px;">
                <p>${message}</p>
            </div>

            <div style="color:white; font-size:2rem; text-align:center; border: 2px solid ${color}; padding: 20px; border-radius: 10px; background: rgba(0,0,0,0.8);">
                <p style="color:#4caf50; margin: 5px;">Successful Events: ${successCount}</p>
                <p style="color:#f44336; margin: 5px;">Failed Events: ${failCount}</p>
            </div>
            
            <br>
            
            <button class="start-btn" onclick="location.href='welcome.html'">
                ${hasWon ? "Play Again" : "Try Again"}
            </button>
        </div>
    `;
}