document.getElementById('pirateForm').addEventListener('submit', function(event) {
    event.preventDefault();

    const captainName = document.getElementById('captainName').value.trim();
    const alias = document.getElementById('alias').value.trim();
    const crewName = document.getElementById('crewName').value.trim();

    const errorMsg = document.getElementById('errorMsg');
    errorMsg.innerText = "";

    if (captainName.length < 3) {
        errorMsg.innerText = "Arr! A Captain's name must be at least 3 letters!";
        return;
    }

    if (alias.length < 1) {
        errorMsg.innerText = "Ye need an alias, or the Marines won't know who to bounty!";
        return;
    }

    if (crewName.length < 5) {
        errorMsg.innerText = "That crew name be too short! It needs at least 5 letters.";
        return;
    }

    localStorage.setItem("captainName", captainName);
    localStorage.setItem("captainAlias", alias);
    localStorage.setItem("crewName", crewName);

    window.location.href = "members.html";
});