document.addEventListener('DOMContentLoaded', function() {

    console.log("DOMContentLoaded event triggered");
    document.getElementById('searchCriteria').addEventListener('change', handleCriteriaChange);
    const searchCriteria = document.getElementById('searchCriteria');
    if (searchCriteria) {
        console.log("searchCriteria element found");
        searchCriteria.addEventListener('change', handleCriteriaChange);
    } else {
        console.error("searchCriteria element not found");
    }

    let selectedRecordId = null;
    let selectedGender = null;
    let selectedRecordData = null;

function convertToDateFormat(dateStr) {
    if (!dateStr) {
        console.error("תאריך לא מוגדר או לא תקין");
        return "";
    }
    const parts = dateStr.split('-');
    if (parts.length !== 3) {
        console.error("תאריך בפורמט שגוי");
        return "";
    }

    const [year, month, day] = parts.map(Number);

    if (isNaN(year) || isNaN(month) || isNaN(day)) {
        console.error("תאריך כולל ערכים לא תקינים");
        return "";
    }

    // המרת לפורמט yyyy-MM-dd
    return `${year}-${month.toString().padStart(2, '0')}-${day.toString().padStart(2, '0')}`;
}


// פונקציה לחישוב גיל
function calculateAge(dateOfBirthId, ageFieldId) {
    const dateOfBirthStr = document.getElementById(dateOfBirthId).value.trim();
    const dob = new Date(dateOfBirthStr);
    const today = new Date();

    // בדיקת אם התאריך שנכנס תקין
    if (isNaN(dob.getTime())) {
        document.getElementById(ageFieldId).value = 'Invalid date';
        return;
    }

    let age = today.getFullYear() - dob.getFullYear();
    const monthDifference = today.getMonth() - dob.getMonth();

    if (monthDifference < 0 || (monthDifference === 0 && today.getDate() < dob.getDate())) {
        age--;
    }

    document.getElementById(ageFieldId).value = age;
}

// הקשבה לשינויים בתאריך הלידה של הגברים
document.getElementById('UpDateOfBirth').addEventListener('change', function() {
    calculateAge('UpDateOfBirth', 'UpAge');
});


const criteriaOptions = {
    status: [
        { value: '', text: '' },
        { value: 'רווק/ה', text: 'רווק/ה' },
        { value: 'גרוש/ה', text: 'גרוש/ה' },
        { value: 'אלמן/ה', text: 'אלמן/ה' },
        { value: 'נשוי/ה', text: 'נשוי/ה' },
        { value: 'לא רלוונטי', text: 'לא רלוונטי' }
    ],
    style: [
        { value: '', text: '' },
        { value: 'חרדי/ת ספרדי/ת', text: 'חרדי/ת ספרדי/ת' },
        { value: 'חרדי/ת אשכנזי/ת', text: 'חרדי/ת אשכנזי/ת' },
        { value: 'חרדי/ת', text: 'חרדי/ת' },
        { value: 'חרדי/ת-מודרני', text: 'חרדי/ת-מודרני' },
        { value: 'דתי/יה', text: 'דתי/יה' },
        { value: 'דתי/יה לאומי/ת', text: 'דתי/יה לאומי/ת' },
        { value: 'חבדניק/ית', text: 'חבדניק/ית' },
        { value: 'ברסלב/ית', text: 'ברסלב/ית' }
    ]
};

function handleCriteriaChange() {
    console.log('handleCriteriaChange called', criteriaOptions);
    const criteria = document.getElementById("searchCriteria").value; // שינוי מ-searchCriteria ל-criteria
    const inputContainer = document.getElementById("inputContainer");
    console.log(`Selected criteria: ${criteria}`);

    // נקה את התוכן הקודם של ה-inputContainer
    inputContainer.innerHTML = '';

    if (criteriaOptions[criteria]) { // שימוש ב-criteria במקום searchCriteria
        const select = document.createElement('select');
        select.id = `${criteria}Select`; // שימוש ב-criteria במקום searchCriteria

        criteriaOptions[criteria].forEach(option => {
            const opt = document.createElement('option');
            opt.value = option.value;
            opt.textContent = option.text;
            select.appendChild(opt);
        });

        inputContainer.appendChild(select);
    } else {
        const input = document.createElement('input');
        input.type = 'text';
        input.id = 'searchInput';
        input.placeholder = 'הערך לחיפוש';
        inputContainer.appendChild(input);
    }
}

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
                     throw new Error("לא נמצאה רשומה לפי החיפוש.");
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
                            <td>${result.seeking}</td>
                            <td>${result.phone}</td>
                            <td>${result.createdAt}</td>
                            <td>${result.updatedAt}</td>
                            <td>${result.height}</td>
                            <td>${result.status}</td>
                            <td>${result.location}</td>
                            <td>${result.dateOfBirth}</td>
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
            alert(error.message); // הצגת הודעה על כך שלא נמצאו תוצאות
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

        const recordId = cells[cells.length -1].innerText;
        console.log(recordId);
        if(selectedGender === 'men'){
            selectedRecordData = {
                    byWomenId: recordId,
                };
        }else if(selectedGender === 'women'){
             selectedRecordData = {
                    byMenId: recordId,
                };
        }
//        selectedRecordData = {
//
//            height: cells[0].innerText,
//            status: cells[1].innerText,
//            location: cells[2].innerText,
//            dateOfBirth: cells[3].innerText,
//            age: cells[4].innerText,
//            lastName: cells[5].innerText,
//            firstName: cells[6].innerText,
//            style: cells[7].innerText,
//            community: cells[8].innerText,
//            headCovering: cells[9].innerText,
//            device: cells[10].innerText,
//        };

        // הוספת מחלקת "נבחר" לשורה שנבחרה
        row.classList.add('selected');
    }

function runJobFromDate(){
 const genderFromDate = document.querySelector('input[name="gender"]:checked').value;
console.log(genderFromDate);
    let url = genderFromDate === 'men' ? `http://localhost:8080/api/men/run-job` : `http://localhost:8080/api/women/run-job`;
    console.log(url);
    fetch(url)
//fetch('http://localhost:8080/api/men/run-job')
  .then(response => response.text())
  .then(data => {
    console.log(data); // בדוק את התגובה מה-API
    //genderFromDate = null;
  })
  .catch(error => console.error('Error:', error));
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
    console.log("Selected Record ID:", selectedRecordId);
    console.log("Selected Gender:", selectedGender);
       let url;
       if (selectedGender === 'men') {
             url = `http://localhost:8080/api/men/${selectedRecordId}`;
       }else{
             url = `http://localhost:8080/api/women/${selectedRecordId}`;
       }

        const modal = document.getElementById("updateModal");
        modal.style.display = "block";
        console.log(url);
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
    console.log(data);
    document.getElementById('UpId').value = data.id || '';
    document.getElementById('UpHeight').value = data.height || '';
    document.getElementById('UpStatus').value = data.status || '';
    document.getElementById('UpLocation').value = data.location || '';
    document.getElementById('UpDateOfBirth').value = data.dateOfBirth || '';
    document.getElementById('UpAge').value = data.age || '';
    document.getElementById('UpLastName').value = data.lastName || '';
    document.getElementById('UpFirstName').value = data.firstName || '';
    document.getElementById('UpStyle').value = data.style || '';
    document.getElementById('UpCommunity').value = data.community || '';
    document.getElementById('UpHeadCovering').value = data.headCovering || '';
    document.getElementById('UpDevice').value = data.device || '';
    document.getElementById('UpPhone').value = data.phone || '';
    document.getElementById('UpSeeking').value = data.phone || '';

            const currentProfileImage = document.getElementById('currentProfileImage');
            const noProfileImageText = document.getElementById('noProfileImageText');
            if (data.profilePictureUrl) {
            //???תהליך לביצוע שהתמונה תוצג תקין מיד ולא אחרי הורדת השרת
                currentProfileImage.src = `http://localhost:8080/images/${data.profilePictureUrl}?timestamp=${new Date().getTime()}`;

                currentProfileImage.style.display = 'block';
                noProfileImageText.style.display = 'none';
            } else {
                currentProfileImage.style.display = 'none';
                noProfileImageText.style.display = 'block';
            }

            const currentAdditionalImage = document.getElementById('currentAdditionalImage');
            const noCurrentAdditionalImageText = document.getElementById('noCurrentAdditionalImageText');
            if (data.additionalPictureUrl) {
            //???תהליך לביצוע שהתמונה תוצג תקין מיד ולא אחרי הורדת השרת
                currentAdditionalImage.src = `http://localhost:8080/images/${data.additionalPictureUrl}?timestamp=${new Date().getTime()}`;

                currentAdditionalImage.style.display = 'block';
                noCurrentAdditionalImageText.style.display = 'none';
            } else {
                currentAdditionalImage.style.display = 'none';
                noCurrentAdditionalImageText.style.display = 'block';
            }
})

    .catch(error => {
        console.error('Error fetching record for update:', error);
    });
}

async function saveUpdateData() {
    const status = document.getElementById('UpStatus').value.trim();
    const firstName = document.getElementById('UpFirstName').value.trim();
    const lastName = document.getElementById('UpLastName').value.trim();
    const dateOfBirth = document.getElementById('UpDateOfBirth').value.trim();
    const age = document.getElementById('UpAge').value.trim();
    const height = document.getElementById('UpHeight').value.trim();
    const location = document.getElementById('UpLocation').value.trim();
    const style = document.getElementById('UpStyle').value.trim();
    const community = document.getElementById('UpCommunity').value.trim();
    const headCovering = document.getElementById('UpHeadCovering').value.trim();
    const device = document.getElementById('UpDevice').value.trim();
    const phone = document.getElementById('UpPhone').value.trim();
    const seeking = document.getElementById('UpSeeking').value.trim();

    const profilePictureUrl = document.getElementById("UpProfilePicture").files[0];
    const additionalPictureUrl = document.getElementById("UpAdditionalPicture").files[0];
    const deleteProfilePicture = document.getElementById("deleteProfilePicture").checked;
    const deleteAdditionalPicture = document.getElementById("deleteAdditionalPicture").checked;


    if (!status || !firstName || !lastName || !age || !height || !location || !style) {
        alert(' אנא מלא את כל השדות הנדרשים: סטטוס, סגנון, מיקום, גיל, גובה, שם ומשפחה.');
        return;
    }

    if (!/^\d+$/.test(height)) {
        alert(' הכנס גובה בפורמט תקין, לדוגמה: 156');
        return;
    }

    const updatedData = {
        status,
        firstName,
        lastName,
        dateOfBirth,
        age,
        height,
        location,
        style,
        community,
        headCovering,
        device,
        phone,
        seeking,
    };

    const url = selectedGender === 'men' ? `http://localhost:8080/api/men/update/${selectedRecordId}` : `http://localhost:8080/api/women/update/${selectedRecordId}`;

    try {
        const response = await fetch(url, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(updatedData)
        });

    if (!response.ok) {
        const errorMessage = await response.text(); // קבלת ההודעה מהשרת
        throw new Error(errorMessage); // זריקת שגיאה עם ההודעה
    }
        const data = await response.json();
         await sendImages(profilePictureUrl, additionalPictureUrl, deleteProfilePicture, deleteAdditionalPicture);

        console.log('Record updated:', data);
        alert("הרשומה עודכנה בהצלחה!");
        search();
        resetFormFields();
        closeModal('updateModal');
        selectedRecordId = null;
        selectedRecordData = null;
        selectedGender = null;

    }catch (error) {
    console.log('response');
    console.log('Error message:', error.message);
}
}
// פונקציה לשליחת התמונות
async function sendImages(profilePictureUrl, additionalPictureUrl, deleteProfilePicture, deleteAdditionalPicture) {
    const formData = new FormData();

//    if (deleteProfilePicture) {
//        formData.append('deleteProfilePicture', "true");
//    } else if (profilePictureUrl) {
//        formData.append('profilePictureUrl', profilePictureUrl);
//    }
 if (profilePictureUrl) {
        formData.append('profilePictureUrl', profilePictureUrl);
    } else if (deleteProfilePicture) {
         formData.append('deleteProfilePicture', "true");
    }
    if (additionalPictureUrl) {
         formData.append('additionalPictureUrl', additionalPictureUrl);
    }else if (deleteAdditionalPicture) {
         formData.append('deleteAdditionalPicture', "true");
    }

       // const response = await fetch(`http://localhost:8080/api/women/update/images/${selectedRecordId}`, {
    let response = null;
    if(selectedGender === 'men' ){
          response = await fetch(`http://localhost:8080/api/men/update/images/${selectedRecordId}`,
          {
                 method: 'PUT',
                 body: formData,
             });
    }else{
         response = await fetch(`http://localhost:8080/api/women/update/images/${selectedRecordId}`,
        {
                  method: 'PUT',
                  body: formData,
             });
    }
    if (!response.ok) {
        throw new Error(`HTTP error! status: ${response.status}`);
    }
    console.log(response);
    return response.json();
}


    function closeModal(modalId) {
        console.log('test');
        const modal = document.getElementById(modalId);
        modal.style.display = "none";
        resetFormFields();

    }
    function resetFormFields() {
                document.getElementById("UpId").value = "";
                document.getElementById("UpHeight").value = "";
                document.getElementById("UpStatus").value = "";
                document.getElementById("UpLocation").value = "";
                document.getElementById("UpDateOfBirth").value = "";
                document.getElementById("UpAge").value = "";
                document.getElementById("UpLastName").value = "";
                document.getElementById("UpFirstName").value = "";
                document.getElementById("UpStyle").value = "";
                document.getElementById("UpCommunity").value = "";
                document.getElementById("UpHeadCovering").value = "";
                document.getElementById("UpDevice").value = "";
                document.getElementById("UpPhone").value = "";
                document.getElementById("UpHeadCovering").value = "";
                document.getElementById("UpDevice").value = "";
                document.getElementById("UpSeeking").value = "";
                document.getElementById("currentProfileImage").value = "";
                document.getElementById("noProfileImageText").value = "";
                document.getElementById("currentAdditionalImage").value = "";
                document.getElementById("noCurrentAdditionalImageText").value = "";
                document.getElementById("UpProfilePicture").value = "";
                document.getElementById("UpAdditionalPicture").value = "";
                document.getElementById("deleteProfilePicture").checked = "";
                document.getElementById("deleteAdditionalPicture").checked = "";

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
    window.convertToDateFormat = convertToDateFormat;
    window.selectRecord = selectRecord;
    window.deleteSelected = deleteSelected;
    window.updateSelected = updateSelected;
    window.calculateAge = calculateAge;
    window.saveUpdateData = saveUpdateData;
    window.closeModal = closeModal;
    window.openMenModal = openMenModal;
    window.openWomenModal = openWomenModal;
    window.openMatchModal = openMatchModal;
    window.resetFormFields = resetFormFields;
    window.runJobFromDate = runJobFromDate;
});
