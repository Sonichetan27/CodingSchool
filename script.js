function toggleDropdown() {
      const dropdown = document.getElementById("dropdown-content");
      dropdown.style.display = dropdown.style.display === "block" ? "none" : "block";
    }
    window.onclick = function(e) {
      if (!e.target.matches("button")) {
        const dropdown = document.getElementById("dropdown-content");
        if (dropdown && dropdown.style.display === "block") {
          dropdown.style.display = "none";
        }
      }
    }
  