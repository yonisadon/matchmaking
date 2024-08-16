document.addEventListener('DOMContentLoaded', function() {
    let selectedRecordId = null;
    let selectedGender = null;
    let selectedRecordData = null;

    function search() {

        deleteParamsInTable();

        const searchTerm = document.getElementById('searchInput').value;
        const gender = document.querySelector('input[name="gender"]:checked').value;
        const criteria = document.getElementById('searchCriteria').value;

        console.log(gender);
        console.log(criteria);
        let url;
        if (criteria === 'all') {
            url = gender === 'women' ? 'http://localhost:8080/api/women/searchAll' : 'http://localhost:8080/api/men/searchAll';
        } else {
            url = gender === 'men' ? `http://localhost:8080/api/men/search?term=${searchTerm}&criteria=${criteria}` : `http://localhost:8080/api/women/search?term=${searchTerm}&criteria=${criteria}`;
        }

        console.log(`Fetching: ${url}`);
        fetch(url)
            .then(response => {
                if (!response.ok) {
                    throw new Error(`HTTP error! status: ${response.status}`);
                }
                return response.json();
            })
            .then(data => {
                const tableBody = document.querySelector('#resultsTable tbody');
                tableBody.innerHTML = '';

                // איפוס הבחירה של השורה הנוכחית
                selectedRecordId = null;
                selectedGender = null;
                selectedRecordData = null;

                if (data.length > 0) {
                    data.forEach(result => {
                        const row = document.createElement('tr');
                        row.innerHTML = `
                            <td>${result.height}</td>
                            <td>${result.status}</td>
                            <td>${result.location}</td>
                            <td>${result.age}</td>
                            <td>${result.lastName}</td>
                            <td>${result.firstName}</td>
                            <td>${result.style}</td>
                            <td>${result.community}</td>
                            <td>${result.headCovering}</td>
                            <td>${result.device}</td>
                            <td>${result.id}</td>
                        `;
                        row.onclick = () => selectRecord(result.id, gender, row);
                        tableBody.appendChild(row);
                    });
                }
            })
            .catch(error => {
                console.error('Error fetching search results:', error);
            });
    }

    function selectRecord(id, gender, row) {
        if (selectedRecordId === id) {
            alert("כבר נבחרה רשומה זו. אנא בחר רשומה אחרת.");
            return;
        }
        selectedRecordId = id;
        selectedGender = gender;

       console.log("gender is: ", selectedGender);
       let url;
       url = selectedGender === 'men' ? `http://localhost:8080/api/preferences_men/byMenId/${selectedRecordId}` : `http://localhost:8080/api/preferences_women/byWomenId/${selectedRecordId}`;

        //const url = `http://localhost:8080/api/preferences_men/byMenId/${selectedRecordId}`;
        fetch(url, {
            method: 'GET'
        })
        .then(response => {
            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }
            return response.json();
        })
        .then(data => {
            console.log("Data received from backend:", data);

            // הסרת בחירה מכל השורות
            const rows = document.querySelectorAll('#resultsTable tbody tr');
            rows.forEach(r => r.classList.remove('selected'));

            // הוספת מחלקת "נבחר" לשורה שנבחרה
            row.classList.add('selected');

            // הצגת הנתונים בטבלה השמאלית
            displayPreferencesInLeftTable(data);
        })
        .catch(error => {
            console.error('Error fetching record:', error);
        });
    }

    function displayPreferencesInLeftTable(data) {
        const leftTableBody = document.querySelector('#resultsTableFromFindingMatch tbody');
        leftTableBody.innerHTML = ''; // נקה את הטבלה הקיימת

        const row = document.createElement('tr');
        const ageRange = `${data.preferredAgeFrom || ''}-${data.preferredAgeTo || ''}`;
        const heightRange = `${data.preferredHeightFrom || ''}-${data.preferredHeightTo || ''}`;
        row.innerHTML = `

            <td>${ageRange}</td>
            <td>${heightRange}</td>
            <td>${data.preferredCommunity || ''}</td>
            <td>${data.kosherOrNonKosherDevice || ''}</td>
            <td>${data.preferredStatus || ''}</td>
            <td>${data.preferredRegion || ''}</td>
            <td>${data.handkerchiefOrWig || ''}</td>
            <td>${data.preferredStyle || ''}</td>
            <td>${data.idPreferencesMen || ''}</td>
            ${selectedGender === 'men' ? `<td>${data.menId || ''}</td>` : `<td>${data.womenId || ''}</td>`}
        `;
        leftTableBody.appendChild(row);
    }

    function updateSelected() {
        if (!selectedRecordId) {
            alert(".אנא בחר רשומה לעדכון");
            return;
        }
    }

function searchMatchesSelected() {
    if (!selectedRecordId) {
        alert("אנא בחר רשומה לחיפוש התאמה.");
        return;
    }

       let url;
       url = selectedGender === 'men' ? `http://localhost:8080/api/men/searchMatches?menId=${selectedRecordId}` : `http://localhost:8080/api/women/searchMatches?womenId=${selectedRecordId}`;
        fetch(url, {
            method: 'GET'
        })
    //fetch(`http://localhost:8080/api/men/searchMatches?menId=${selectedRecordId}`)
        .then(response => response.json())
        .then(data => {
            const tableBody = document.querySelector('#tableFromFindingOtherSide tbody');
            tableBody.innerHTML = '';

            if (data.length > 0) {
                data.forEach(result => {
                    const row = document.createElement('tr');
                    row.innerHTML = `
                        <td>${result.height}</td>
                        <td>${result.status}</td>
                        <td>${result.location}</td>
                        <td>${result.age}</td>
                        <td>${result.lastName}</td>
                        <td>${result.firstName}</td>
                        <td>${result.style}</td>
                        <td>${result.community}</td>
                        <td>${result.headCovering}</td>
                        <td>${result.device}</td>
                        <td>${result.id}</td>
                    `;
                    tableBody.appendChild(row);
                });
            }
        })
        .catch(error => {
            console.error('Error fetching search results:', error);
        });
}


       function deleteParamsInTable(){
            const leftTableBody = document.querySelector('#resultsTableFromFindingMatch tbody');
            leftTableBody.innerHTML = ''; // נקה את הטבלה הקיימת
       }
    window.search = search;
    window.selectRecord = selectRecord;
    window.updateSelected = updateSelected;
    window.searchMatchesSelected = searchMatchesSelected;
});

function openHomeModal() {
    // פותח את העמוד החדש
    window.location.href = "home.html";
}



