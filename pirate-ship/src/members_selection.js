// 1. Define the order of recruitment
const roles = [
    { name: "Second", desc: "Your second-in-command. Needs high leadership!" },
    { name: "Navigator", desc: "Charts the course. Needs high intelligence!" },
    { name: "Sniper", desc: "Protects the ship from afar. Needs accuracy!" },
    { name: "Cook", desc: "Keeps morale high. Needs cooking skills!" },
    // Add other roles here...
];

let currentRoleIndex = 0;
const container = document.getElementById('candidatesContainer');
const title = document.getElementById('roleTitle');
const desc = document.getElementById('roleDesc');

// 2. Start the process
loadNextRole();

function loadNextRole() {
    // Check if we are done
    if (currentRoleIndex >= roles.length) {
        alert("Crew assembly complete! Setting sail...");
        // window.location.href = "adventure.html"; // Go to game
        return;
    }

    // Update Header
    const currentRole = roles[currentRoleIndex];
    title.innerText = "Recruiting: " + currentRole.name;
    desc.innerText = currentRole.desc;

    // Clear previous cards
    container.innerHTML = "";

    // Fetch candidates (Simulating your DB call here)
    const candidates = mockFetchCandidates(currentRole.name);

    // Create HTML for each candidate
    candidates.forEach(pirate => {
        createPirateCard(pirate);
    });
}

function createPirateCard(pirate) {
    // 1. Select Random Image based on Sex
    const imagePath = getRandomImage(pirate.sex);

    // 2. Build the HTML Card
    const card = document.createElement('div');
    card.className = 'pirate-card';

    card.innerHTML = `
        <img src="${imagePath}" alt="Pirate Portrait" class="pirate-img">
        <h2 class="pirate-name">${pirate.name}</h2>
        <p class="pirate-alias">"${pirate.alias}"</p>
        
        <ul class="stat-list">
            <li><span>Power:</span> <span>${pirate.stats.power}</span></li>
            <li><span>Skill:</span> <span>${pirate.stats.skill}</span></li>
            <li><span>Loyalty:</span> <span>${pirate.stats.loyalty}</span></li>
        </ul>

        <button class="recruit-btn">Recruit</button>
    `;

    // 3. Add Click Event to the Button
    card.querySelector('.recruit-btn').addEventListener('click', () => {
        console.log(`Recruited ${pirate.name} as ${roles[currentRoleIndex].name}`);

        // TODO: Send selection to your Java Backend here

        // Move to next role
        currentRoleIndex++;
        loadNextRole();
    });

    // 4. Add to page
    container.appendChild(card);
}

// --- Helper Functions ---

function getRandomImage(sex) {
    // Random number between 1 and 5
    const randomNum = Math.floor(Math.random() * 5) + 1;

    if (sex === 'F') {
        return `img/friendly/fem${randomNum}.png`;
    } else {
        return `img/friendly/male${randomNum}.png`;
    }
}

// SIMULATION: This replaces your database call for now
function mockFetchCandidates(role) {
    // In real life, this would be: fetch('/api/getCandidates?role=' + role)

    // Generating 3 fake pirates
    return [
        { name: "Barnaby", alias: "The Rusty", sex: "M", stats: { power: 85, skill: 40, loyalty: 90 } },
        { name: "Eliza", alias: "Stormborn", sex: "F", stats: { power: 70, skill: 95, loyalty: 60 } },
        { name: "Grog", alias: "Big Grog", sex: "M", stats: { power: 99, skill: 10, loyalty: 50 } }
    ];
}