document.getElementById('pirateForm').addEventListener('submit', function(event) {
    event.preventDefault(); // Stop the form from reloading the page

    // 1. Get Values
    const captainName = document.getElementById('captainName').value.trim();
    const alias = document.getElementById('alias').value.trim();
    const crewName = document.getElementById('crewName').value.trim();
    const errorMsg = document.getElementById('errorMsg');

    // 2. Reset Errors
    errorMsg.innerText = "";

    // 3. Validation
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

    // 4. SAVE DATA (Optional but recommended)
    // This saves the inputs so you can use them on the next page
    localStorage.setItem("captainName", captainName);
    localStorage.setItem("captainAlias", alias);
    localStorage.setItem("crewName", crewName);

    // 5. ROUTE TO NEW PAGE
    // This effectively "clicks the link" via code
    window.location.href = "members.html";
});