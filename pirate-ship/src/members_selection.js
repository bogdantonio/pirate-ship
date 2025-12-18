const roles = [
    { name: "Second", desc: "Who will have the honor to be your second?" },
    { name: "Navigator", desc: "Who will chart your course?" },
    { name: "Sniper", desc: "Who will watch your back from a distance?" },
    { name: "Cook", desc: "KWho will keep the crew fed and happy?" },
    { name: "Archeologist", desc: "Who will uncover the secrets of the past?"},
    { name: "Doctor", desc: "Who will patch up your wounds?"},
    { name: "Shipwright", desc: "Who will keep the ship afloat?"},
    { name: "Musician", desc: "ho will keep the morale high?"},
    { name: "Helmsman", desc: "Who will steer the ship through the storm?"},
];

let currentRoleIndex = 0;
const container = document.getElementById('candidatesContainer');
const title = document.getElementById('roleTitle');
const desc = document.getElementById('roleDesc');

loadNextRole();

// Change function to 'async' so we can wait for the database response
async function loadNextRole() {
    // Check if we are done
    if (currentRoleIndex >= roles.length) {
        finalizeCrew(); // Trigger the final save
        return;
    }

    const currentRole = roles[currentRoleIndex];
    title.innerText = "Recruiting: " + currentRole.name;
    desc.innerText = currentRole.desc;

    // Show a loading text while waiting for Database
    container.innerHTML = "<h2 style='color:white;'>Searching the taverns...</h2>";

    try {
        // --- REAL DATABASE CALL ---
        // We ask the Java Server: "Give me 3 random candidates for this role"
        const response = await fetch(`http://localhost:8080/api/candidates?role=${currentRole.name}`);

        if (!response.ok) {
            throw new Error(`Server Error: ${response.status}`);
        }

        const candidates = await response.json();

        // Clear loading text
        container.innerHTML = "";

        // Create cards for the real data
        candidates.forEach(pirate => {
            createPirateCard(pirate);
        });

    } catch (error) {
        console.error("Failed to fetch candidates:", error);
        container.innerHTML = "<h2 style='color:red;'>Could not find any pirates. Is the Java Server running?</h2>";
    }

    function createPirateCard(pirate) {
        const imagePath = getRandomImage(pirate.sex);
        const card = document.createElement('div');
        card.className = 'pirate-card';

        let roleStatsHTML = '';
        if (pirate.roleData) {
            for (const [key, value] of Object.entries(pirate.roleData)) {
                // Beautify key: "weather_prediction" -> "Weather Prediction"
                const label = key.replace(/_/g, ' ').replace(/\b\w/g, c => c.toUpperCase());
                roleStatsHTML += `<li><span>${label}:</span> <span class="special-stat">${value}</span></li>`;
            }
        }

        card.innerHTML = `
        <img src="${imagePath}" alt="Pirate Portrait" class="pirate-img">
        <h2 class="pirate-name">${pirate.name}</h2>
        <p class="pirate-alias">"${pirate.alias}"</p>
        
        <ul class="stat-list">
            <li><span>Strength:</span> <span>${pirate.stats.strength}</span></li>
            <li><span>Agility:</span> <span>${pirate.stats.agility}</span></li>
            <li><span>Endurance:</span> <span>${pirate.stats.endurance}</span></li>
            <li><span>Intelligence:</span> <span>${pirate.stats.intelligence}</span></li>
            <li><span>Charisma:</span> <span>${pirate.stats.charisma}</span></li>
            <li><span>Willpower:</span> <span>${pirate.stats.willpower}</span></li>
        </ul>

        <div class="stat-separator">Class Skills</div>

        <ul class="stat-list role-specific-list">
            ${roleStatsHTML}
        </ul>

        <button class="recruit-btn">Recruit</button>
    `;

        card.querySelector('.recruit-btn').addEventListener('click', () => {
            const currentRoleName = roles[currentRoleIndex].name.toLowerCase();

            console.log(`Recruited ${pirate.name} as ${currentRoleName}`);

            localStorage.setItem(`selected_${currentRoleName}`, JSON.stringify(pirate));

            currentRoleIndex++;
            loadNextRole();
        });

        container.appendChild(card);
    }

    function finalizeCrew() {
        container.innerHTML = "<h2 style='color:gold; text-align:center;'>Signing the logbook...</h2>";

        const finalData = {
            captainName: localStorage.getItem("captainName"),
            alias: localStorage.getItem("captainAlias"),
            crewName: localStorage.getItem("crewName"),
        };

        roles.forEach(role => {
            const prefix = role.name.toLowerCase(); // e.g., "second", "navigator"
            const pirateJson = localStorage.getItem(`selected_${prefix}`);

            if (pirateJson) {
                const pirate = JSON.parse(pirateJson);

                // A. Basic Pirate Data
                finalData[`${prefix}_name`] = pirate.name;
                finalData[`${prefix}_alias`] = pirate.alias;
                finalData[`${prefix}_sex`] = pirate.sex;

                // B. Send ALL Base Stats (Strength, Agility, etc.)
                for (const [statKey, statValue] of Object.entries(pirate.stats)) {
                    finalData[`${prefix}_${statKey}`] = statValue;
                }

                // C. Send ALL Role Specific Data (Navigation, Cooking, etc.)
                if (pirate.roleData) {
                    for (const [roleKey, roleValue] of Object.entries(pirate.roleData)) {
                        finalData[`${prefix}_${roleKey}`] = roleValue;
                    }
                }
            }
        });

        fetch('http://localhost:8080/api/create-crew', {
            method: 'POST',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify(finalData)
        })
            .then(response => response.json())
            .then(data => {
                if (data.error) {
                    alert("Error: " + data.error);
                    container.innerHTML = "<h2 style='color:red'>Failed to set sail!</h2>";
                } else {
                    // --- CRITICAL STEP: Save the Crew ID ---
                    // You need this ID to save the Adventure result later!
                    if (data.crewId) {
                        localStorage.setItem("crewId", data.crewId);
                    }

                    // alert("Success! " + data.message);
                    window.location.href = "start_adventure.html";
                }
            })
            .catch(err => {
                console.error(err);
                container.innerHTML = "<h2 style='color:red'>Server Connection Failed</h2>";
            });
    }

    function getRandomImage(sex) {
        // Random number between 1 and 5
        const randomNum = Math.floor(Math.random() * 5) + 1;

        if (sex === 'F') {
            return `img/friendly/fem${randomNum}.png`;
        } else {
            return `img/friendly/male${randomNum}.png`;
        }
    }
}