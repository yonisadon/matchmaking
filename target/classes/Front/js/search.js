document.addEventListener('DOMContentLoaded', function() {
    let selectedRecordId = null;
    let selectedGender = null;
    let selectedRecordData = null;

    function search() {
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

        // הסרת בחירה מכל השורות
        const rows = document.querySelectorAll('#resultsTable tbody tr');
        rows.forEach(r => r.classList.remove('selected'));

        const cells = row.getElementsByTagName("td");
        selectedRecordData = {
            height: cells[0].innerText,
            status: cells[1].innerText,
            location: cells[2].innerText,
            age: cells[3].innerText,
            lastName: cells[4].innerText,
            firstName: cells[5].innerText,
            style: cells[6].innerText,
            community: cells[7].innerText,
            headCovering: cells[8].innerText,
            device: cells[9].innerText,
        };

        // הוספת מחלקת "נבחר" לשורה שנבחרה
        row.classList.add('selected');
    }

function deleteSelected() {
    if (!selectedRecordId) {
        alert("אנא בחר רשומה למחיקה.");
        return;
    }

    if (confirm("בטוח שזו הרשומה למחיקה?")) {
        const url = selectedGender === 'men'
            ? `http://localhost:8080/api/men/delete/${selectedRecordId}`
            : `http://localhost:8080/api/women/delete/${selectedRecordId}`;

        fetch(url, {
            method: 'DELETE'
        })
        .then(response => {
            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }
            console.log(`Record with id ${selectedRecordId} deleted successfully`);
            search(); // רענן את תוצאות החיפוש לאחר המחיקה
            selectedRecordId = null; // איפוס ה-ID הנבחר לאחר המחיקה
            selectedGender = null; // איפוס המגדר הנבחר לאחר המחיקה
        })
        .catch(error => {
            console.error('Error deleting record:', error);
        });
    }
}

    function updateSelected() {
        if (!selectedRecordId) {
            alert("אנא בחר רשומה לעדכון.");
            return;
        }

        const modal = document.getElementById("updateModal");
        modal.style.display = "block";
        // מילוי השדות בטופס לפי הנתונים של הרשומה הנבחרת
        document.getElementById('UpStatus').value = selectedRecordData.status;
        document.getElementById('UpFirstName').value = selectedRecordData.firstName;
        document.getElementById('UpLastName').value = selectedRecordData.lastName;
        document.getElementById('UpAge').value = selectedRecordData.age;
        document.getElementById('UpHeight').value = selectedRecordData.height;
        document.getElementById('UpLocation').value = selectedRecordData.location;
        document.getElementById('UpStyle').value = selectedRecordData.style;
        document.getElementById('UpCommunity').value = selectedRecordData.community;
        document.getElementById('UpHeadCovering').value = selectedRecordData.headCovering;
        document.getElementById('UpDevice').value = selectedRecordData.device;

    }

//    function saveUpdateData() {
//        const updatedData = {
//        if(style){
//        }
//
//            status: document.getElementById('UpStatus').value,
//            firstName: document.getElementById('UpFirstName').value,
//            lastName: document.getElementById('UpLastName').value,
//            age: document.getElementById('UpAge').value,
//            height: document.getElementById('UpHeight').value,
//            location: document.getElementById('UpLocation').value,
//            style: document.getElementById('UpStyle').value,
//            community: document.getElementById('UpCommunity').value,
//            headCovering: document.getElementById('UpHeadCovering').value,
//            device: document.getElementById('UpDevice').value,
//        };

function saveUpdateData() {
    const status = document.getElementById('UpStatus').value.trim();
    const firstName = document.getElementById('UpFirstName').value.trim();
    const lastName = document.getElementById('UpLastName').value.trim();
    const age = document.getElementById('UpAge').value.trim();
    const height = document.getElementById('UpHeight').value.trim();
    const location = document.getElementById('UpLocation').value.trim();
    const style = document.getElementById('UpStyle').value.trim();
    const community = document.getElementById('UpCommunity').value.trim();
    const headCovering = document.getElementById('UpHeadCovering').value.trim();
    const device = document.getElementById('UpDevice').value.trim();

    // בדיקת שדות ריקים
    if (!status || !firstName || !lastName || !age || !height || !location || !style ) {
        alert(' אנא מלא את כל השדות הנדרשים: סטטוס, סגנון, מיקום, גיל, גובה שם ומשפחה.');
        return; // עצור את הפעולה אם אחד השדות ריק
    }
  if (!/^\d+$/.test(height)) {
        alert(' הכנס גובה בפורמט תקין, לדוגמה: 156');
        return;
    }

    // יצירת אובייקט מעודכן רק אם כל השדות מלאים
        const updatedData = {
            status,
            firstName,
            lastName,
            age,
            height,
            location,
            style,
            community,
            headCovering,
            device,
        };

        const url = selectedGender === 'men' ? `http://localhost:8080/api/men/update/${selectedRecordId}` : `http://localhost:8080/api/women/update/${selectedRecordId}`;

        fetch(url, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(updatedData)
        })
        .then(response => {
            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }
            return response.json();
        })
        .then(data => {
            console.log('Record updated:', data);
            alert("הרשומה עודכנה בהצלחה!");
            closeModal('updateModal');
            selectedRecordId = null;
            selectedRecordData = null;
            selectedGender = null;
        })
        .catch(error => {
            console.error('Error updating record:', error);
        });
    }

    function closeModal(modalId) {
        const modal = document.getElementById(modalId);
        modal.style.display = "none";
    }

    function openMenModal() {
        document.getElementById('menModal').style.display = 'block';
    }

    function openMatchModal() {
                // פותח את העמוד החדש
                window.location.href = "matches.html";
    }

    function openWomenModal() {
        document.getElementById('womenModal').style.display = 'block';
    }

    window.search = search;
    window.selectRecord = selectRecord;
    window.deleteSelected = deleteSelected;
    window.updateSelected = updateSelected;
    window.saveUpdateData = saveUpdateData;
    window.closeModal = closeModal;
    window.openMenModal = openMenModal;
    window.openWomenModal = openWomenModal;
    window.openMatchModal = openMatchModal;
});
