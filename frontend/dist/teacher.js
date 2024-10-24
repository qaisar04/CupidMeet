document.addEventListener("DOMContentLoaded", function() {
    const password = prompt("Введите пароль для доступа к результатам:");
    if (password !== "Ис22-03") {
        alert("Неверный пароль!");
        window.location.href = "index.html"; // Перенаправление на главную страницу при неверном пароле
        return;
    }

    const resultsTableBody = document.querySelector("#results-table tbody");
    const results = JSON.parse(localStorage.getItem("testResults")) || [];

    if (results.length === 0) {
        resultsTableBody.innerHTML = "<tr><td colspan='3'>Нет данных</td></tr>";
        return;
    }

    
    results.forEach(result => {
        const row = document.createElement("tr");
        row.innerHTML = `
            <td>${result.name}</td>
            <td>${result.group}</td>
            <td>${result.score}/100</td>
        `;
        resultsTableBody.appendChild(row);
    });
});
