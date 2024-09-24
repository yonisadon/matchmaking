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

    let gender = null;
    let selectedRecordId = null;
    let selectedGender = null;
    let selectedRecordData = null;
    let sideElement;

const criteriaOptions = {
    status: [
        { value: 'רווק/ה', text: 'רווק/ה' },
        { value: 'גרוש/ה', text: 'גרוש/ה' },
        { value: 'אלמן/ה', text: 'אלמן/ה' },
        { value: 'נשוי/ה', text: 'נשוי/ה' }
    ],
    style: [
        { value: 'modern', text: 'מודרני' },
        { value: 'traditional', text: 'מסורתי' }
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
        deleteParamsInTable();
    console.log("test");
//        const searchTerm = document.getElementById('searchInput').value;
        let searchTerm;
        gender = document.querySelector('input[name="gender"]:checked').value;
        const criteria = document.getElementById('searchCriteria').value;

    if (criteriaOptions[criteria]) {
        searchTerm = document.getElementById(`${criteria}Select`).value;
    } else {
        searchTerm = document.getElementById('searchInput').value;
    }

        console.log(gender);
        console.log(criteria);
        let url;
        if (criteria === 'all') {
            url = gender === 'women' ? 'http://localhost:8080/api/women/searchAll' : 'http://localhost:8080/api/men/searchAll';
        } else {
            url = gender === 'men' ? `http://localhost:8080/api/men/search?term=${searchTerm}&criteria=${criteria}` : `http://localhost:8080/api/women/search?term=${searchTerm}&criteria=${criteria}`;
        }

        console.log(`Fetching: ${url}`);
        const columns =  ['height', 'status', 'location', 'age', 'lastName', 'firstName', 'style', 'community', 'headCovering', 'device', 'id'];
        FetchService.fetchData(url, 'GET')
        .then(data => {
                console.log("No results found");

            if (data.length === 0) {
                console.log("No results found");
                ResourceNotFoundException();
            } else {
            UIService.displayResults(data, 'resultsTable', columns, gender)
                // איפוס הבחירה של השורה הנוכחית
                selectedRecordId = null;
                selectedGender = null;
                selectedRecordData = null;

console.log("Selected Gender:", gender); // בדוק את הערך בשלבים שונים
}
            })
            .catch(error => {
                console.error('Error fetching search results:', error);
            });
    }



function selectRecord(id, gender, row) {

    const allRows = row.closest('tbody').querySelectorAll('tr');
    allRows.forEach(row => row.classList.remove('selected'));
    selectedRecordId = id;
    selectedGender = gender;

console.log("Selected Gender:", selectedGender); // בדוק את הערך בשלבים שונים
    console.log(gender);

    console.log("Selected Record ID:", selectedRecordId);
    console.log("Selected Gender:", selectedGender);
    const tbody = row.closest('tbody');
    if (!tbody) {
        console.error('Tbody not found');
        return;
    }

    sideElement = tbody.id;
    console.log("Tbody ID:", sideElement);
    console.log('Selected Gender:', selectedGender);
    console.log('Row:', row);
    if (sideElement === 'leftB'){
        const cells = row.getElementsByTagName("td");
        const recordId = cells[cells.length - 2].innerText;
        console.log(recordId);
    if (selectedGender === 'men') {
        selectedRecordData = {
            byWomenId: recordId,
        };
    } else {
        selectedRecordData = {
            byMenId: recordId,
        };
    }
        console.log("Selected Record ID before return:", selectedRecordId);
        selectedRecordId = recordId;
            console.log("Selected Record ID before return:", selectedRecordId);
            console.log("Record data saved:", selectedRecordData);
            row.classList.add('selected');
            }
    if (sideElement === 'leftA'){

            const cells = row.getElementsByTagName("td");
            const recordId = cells[cells.length - 2].innerText;
            console.log(recordId);
        if (selectedGender === 'women') {
            selectedRecordData = {
                byWomenId: recordId,
            };
        } else {
            selectedRecordData = {
                byMenId: recordId,
            };
        }

    console.log("Selected Record ID before return:", selectedRecordId);
    selectedRecordId = recordId;
        console.log("Selected Record ID before return:", selectedRecordId);
        console.log("Record data saved:", selectedRecordData);
        row.classList.add('selected');

    } else {
        let url;
        console.log("Selected Record ID:", selectedRecordId);
        if (sideElement === 'rightA') {
        deleteParamsInTableMatch();
            url = selectedGender === 'men'
                ? `http://localhost:8080/api/preferences_men/byMenId/${selectedRecordId}`
                : `http://localhost:8080/api/preferences_women/byWomenId/${selectedRecordId}`;

        } else if (sideElement === 'rightB') {
            url = selectedGender === 'men'
                ? `http://localhost:8080/api/preferences_women/byWomenId/${selectedRecordId}`
                : `http://localhost:8080/api/preferences_men/byMenId/${selectedRecordId}`;

        } else {
            console.log('Invalid sideElement value');
            return;
        }
        console.log(url);
    FetchService.fetchData(url, 'GET')

            .then(data => {
                console.log("Data received from backend:", data);
                row.classList.add('selected');
                if (sideElement === 'rightA') {
                    displayPreferencesInLeftTable(data);
                } else if (sideElement === 'rightB') {
                    displayPreferencesInLeftTableSide(data);
                }
            })
            .catch(error => {
                console.error('Error fetching record:', error);
            });
    }
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
            ${selectedGender === 'men' ? `<td>${data.idPreferencesMen || ''}</td>` : `<td>${data.idPreferencesWomen || ''}</td>`}
            ${selectedGender === 'men' ? `<td>${data.menId || ''}</td>` : `<td>${data.womenId || ''}</td>`}
        `;
        row.onclick = () => selectRecord(data.id, selectedGender, row); // עדכון פונקציית onclick
        leftTableBody.appendChild(row);
        console.log(row);
        }

    function displayPreferencesInLeftTableSide(data) {
            const leftTableBody = document.querySelector('#resultsTableFromFindingMatchOtherSide tbody');
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
                ${selectedGender === 'women' ? `<td>${data.idPreferencesMen || ''}</td>` : `<td>${data.idPreferencesWomen || ''}</td>`}
                ${selectedGender === 'women' ? `<td>${data.menId || ''}</td>` : `<td>${data.womenId || ''}</td>`}
            `;
            row.onclick = () => selectRecord(data.id, selectedGender, row); // עדכון פונקציית onclick
            leftTableBody.appendChild(row);
            console.log(row);

}
    function updateSelected() {
    if (!selectedRecordId || !selectedGender) {
        alert("אנא בחר רשומה לעדכון");
        return;
    }

    console.log("Selected Record ID:", selectedRecordId);
    console.log("Selected Gender:", selectedGender);
    console.log("sideElement:", sideElement);

        let url;
    if (sideElement === 'leftA'){
            if (selectedGender === 'men') {
                 url = `http://localhost:8080/api/preferences_men/byMenIdPreferences/${selectedRecordId}`;
              }else{
                 url = `http://localhost:8080/api/preferences_women/byWomenIdPreferences/${selectedRecordId}`;
              }
    }else if (sideElement === 'leftB'){
            if (selectedGender === 'men') {
                 url = `http://localhost:8080/api/preferences_women/byWomenIdPreferences/${selectedRecordId}`;
              }else{
                 url = `http://localhost:8080/api/preferences_men/byMenIdPreferences/${selectedRecordId}`;
             }
}       else {
                // אם לא מתקיים התנאי הנכון, פשוט לצאת מהפונקציה
                console.log("לא ניתן לעדכן רשומה מהטבלאות מצד ימין");
                return;
            }

           const modal = document.getElementById("updateModal");
           modal.style.display = "block";
        console.log(url);
        FetchService.fetchData(url, 'GET')

    .then(data => {
    console.log(data);

    document.getElementById('preferredRegion').value = data.preferredRegion || '';
    document.getElementById('preferredCommunity').value = data.preferredCommunity || '';

    const ageRange = `${data.preferredAgeFrom}-${data.preferredAgeTo}`;
        document.getElementById('preferredAgeRange').value = ageRange;

    const heightRange = `${data.preferredHeightFrom}-${data.preferredHeightTo}`;
        document.getElementById('preferredHeightRange').value = heightRange;

    document.getElementById('handkerchiefOrWig').value = data.handkerchiefOrWig || '';
    document.getElementById('preferredStyle').value = data.preferredStyle || '';
    document.getElementById('kosherOrNonKosherDevice').value = data.kosherOrNonKosherDevice || '';
    document.getElementById('preferredStatus').value = data.preferredStatus || '';

})

    .catch(error => {
        console.error('Error fetching record for update:', error);
    });
}

function saveUpdateData() {

    var preferredAgeRange = document.getElementById('preferredAgeRange').value.trim();
    var preferredHeightRange = document.getElementById('preferredHeightRange').value.trim();

    //if(preferredAgeRange){
         if (!/^\d+\s*-\s*\d+$/.test(preferredAgeRange)) {
             alert('הכנס טווח גילאים בפורמט תקין (לדוגמה: 20-30)');
             return;
         }
    //}
    //if(preferredHeightRange){
         if (!/^\d+\s*-\s*\d+$/.test(preferredHeightRange)) {
             alert('הכנס טווח גובה בפורמט תקין (לדוגמה: 160-180)');
                return;
        }
    //}

    var [preferredAgeFrom, preferredAgeTo] = preferredAgeRange.split('-').map(age => parseInt(age.trim()));
    var [preferredHeightFrom, preferredHeightTo] = preferredHeightRange.split('-').map(height => parseInt(height.trim()));

    if (preferredAgeFrom > preferredAgeTo) {
        alert('הגיל ההתחלתי לא יכול להיות גדול מהגיל הסופי');
        return;
    }

    if (preferredHeightFrom > preferredHeightTo) {
        alert('הגובה ההתחלתי לא יכול להיות גדול מהגובה הסופי');
        return;
    }

    var preferredRegion = document.getElementById('preferredRegion').value;
    var preferredCommunity = document.getElementById('preferredCommunity').value;
    var handkerchiefOrWig = document.getElementById('handkerchiefOrWig').value;
    var preferredStyle = document.getElementById('preferredStyle').value;
    var kosherOrNonKosherDevice = document.getElementById('kosherOrNonKosherDevice').value;
    var preferredStatus = document.getElementById('preferredStatus').value;

        const updatedData = {
             preferredAgeFrom: preferredAgeFrom,
                            preferredAgeTo: preferredAgeTo,
                            preferredHeightFrom: preferredHeightFrom,
                            preferredHeightTo: preferredHeightTo,
                            preferredRegion: preferredRegion,
                            preferredCommunity: preferredCommunity,
                            handkerchiefOrWig: handkerchiefOrWig,
                            preferredStyle: preferredStyle,
                            kosherOrNonKosherDevice: kosherOrNonKosherDevice,
                            preferredStatus: preferredStatus
        };
        console.log(selectedRecordId);
            let url;
            if (sideElement === 'leftA'){
                url = selectedGender === 'men' ? `http://localhost:8080/api/preferences_men/savePreferences/update/${selectedRecordId}` : `http://localhost:8080/api/preferences_women/savePreferences/update/${selectedRecordId}`;

            }else if (sideElement === 'leftB'){
             url = selectedGender === 'men' ? `http://localhost:8080/api/preferences_women/savePreferences/update/${selectedRecordId}` : `http://localhost:8080/api/preferences_men/savePreferences/update/${selectedRecordId}`;
        }

        FetchService.fetchData(url, 'PUT', updatedData)
        .then(data => {
            console.log('Record updated:', data);
            alert("הרשומה עודכנה בהצלחה!");

           if (sideElement === 'leftA'){
            displayPreferencesInLeftTable(data);
            }else if (sideElement === 'leftB'){
            displayPreferencesInLeftTableSide(data)
        }

            closeModal('updateModal');
            resetFormFields();
            selectedRecordId = null;
            selectedRecordData = null;
        })
        .catch(error => {
            console.error('Error updating record:', error);
        });

    }

        function closeModal(modalId) {
            //resetFormFields();
            const modal = document.getElementById(modalId);
            modal.style.display = "none";
        }

function searchMatchesSelected() {
deleteParamsInTableMatch();
   let url;
    if (!selectedRecordId) {
        alert("אנא בחר רשומה לחיפוש התאמה.");
        return;
    }
    if(sideElement === 'rightA'){
    console.log("Selected Record ID from search mathe:", selectedRecordId);
       url = selectedGender === 'men' ? `http://localhost:8080/api/men/searchMatches?menId=${selectedRecordId}` : `http://localhost:8080/api/women/searchMatches?womenId=${selectedRecordId}`;
       }
       else {
               console.log("חיפוש התאמה מאחת הרשומות מהטבלה הראשונה מצד ימין ");
               return;
           }
    console.log("url", url);
    const columns =  ['height', 'status', 'location', 'age', 'lastName', 'firstName', 'style', 'community', 'headCovering', 'device', 'id'];
    FetchService.fetchData(url, 'GET')

        .then(data => {
        UIService.displayResults(data, 'tableFromFindingOtherSide', columns, selectedGender)
        })

        .catch(error => {
            console.error('Error fetching search results:', error);
        });
        console.log(selectedRecordId);
        selectedRecordId = null;
        console.log(selectedRecordId);
}


       function deleteParamsInTable(){
            const leftATableBody = document.querySelector('#resultsTableFromFindingMatch tbody');
            const rightBTableBody = document.querySelector('#tableFromFindingOtherSide tbody');
            const leftBTableBody = document.querySelector('#resultsTableFromFindingMatchOtherSide tbody');
            leftATableBody.innerHTML = ''; // נקה את הטבלה הקיימת
            rightBTableBody.innerHTML = '';
            leftBTableBody.innerHTML = '';

       }
       function deleteParamsInTableMatch(){
            const rightBTableBody = document.querySelector('#tableFromFindingOtherSide tbody');
            const leftBTableBody = document.querySelector('#resultsTableFromFindingMatchOtherSide tbody');
            rightBTableBody.innerHTML = '';
            leftBTableBody.innerHTML = '';
       }
function resetFormFields() {

    document.getElementById("preferredRegion").value = "";
    document.getElementById("preferredCommunity").value = "";
    document.getElementById("handkerchiefOrWig").value = "";
    document.getElementById("preferredStyle").value = "";
    document.getElementById("kosherOrNonKosherDevice").value = "";
    document.getElementById("preferredStatus").value = "";
    document.getElementById("preferredAgeRange").value = "";
    document.getElementById("preferredHeightRange").value = "";
     }

    window.search = search;
    window.selectRecord = selectRecord;
    window.updateSelected = updateSelected;
    window.saveUpdateData = saveUpdateData;
    window.searchMatchesSelected = searchMatchesSelected;
    window.closeModal = closeModal;

});

function openHomeModal() {
    // פותח את העמוד החדש
    window.location.href = "home.html";
}



