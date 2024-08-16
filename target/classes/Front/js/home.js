var modal = document.getElementById("menModal");

var btn = document.getElementById("openModalButton");

function closeModal(modalId) {
var modal = document.getElementById(modalId);
modal.style.display = "none";
}


window.onclick = function(event) {
    if (event.target == modal) {
        modal.style.display = "none";
    }
}

let idMen;
let idWomen;
var currentGender;
// פונקציה לשמירת נתוני MEN
function saveMenData() {
    var status = document.getElementById("menStatus").value;
    var firstName = document.getElementById("menFirstName").value;
    var lastName = document.getElementById("menLastName").value;
    var age = document.getElementById("menAge").value;
    var height = document.getElementById("menHeight").value;
    var location = document.getElementById("menLocation").value;
    var style = document.getElementById("menStyle").value;
    var community = document.getElementById('menCommunity').value;
    var headCovering = document.getElementById('menHeadCovering').value;
    var device = document.getElementById('menDevice').value;
    var seeking = document.getElementById("menSeeking").value;

    // בדיקת שדות ריקים
    if (!status || !firstName || !lastName || !age || !height || !location || !style ) {
        alert(' אנא מלא את כל השדות הנדרשים: סטטוס, סגנון, מיקום, גיל, גובה שם ומשפחה.');
        return; // עצור את הפעולה אם אחד השדות ריק
    }
  if (!/^\d+$/.test(height)) {
        alert(' הכנס גובה בפורמט תקין, לדוגמה: 156');
        return;
    }

    var menData = {
        status: status,
        firstName: firstName,
        lastName: lastName,
        age: age,
        height: height,
        location: location,
        style: style,
        community: community,
        headCovering: headCovering,
        device: device,
        seeking: seeking
    };

    fetch('http://localhost:8080/api/men/saveMen', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(menData)
    })
    .then(response => response.json())
    .then(data => {
        if (data.success) {
            console.log('Men data saved successfully:', menData, data);

            idMen = data.id; // שמירת ה-ID שהתקבל מהשרת
            document.getElementById('idPerson').value = idMen; // עדכון השדה בחלונית העדפות
            console.log(idMen);
            resetFormFields();

            currentGender = "men";
            console.log(currentGender);
            openPreferencesModal(); // פתיחת החלונית להוספת העדפות
        } else {
            console.error('Error saving men data:', data.message);
        }
    })
    .catch(error => {
        console.error('Error saving men data:', error);
    });
}

function saveWomenData() {
    var status = document.getElementById("womenStatus").value;
    var firstName = document.getElementById("womenFirstName").value;
    var lastName = document.getElementById("womenLastName").value;
    var age = document.getElementById("womenAge").value;
    var height = document.getElementById("womenHeight").value;
    var location = document.getElementById("womenLocation").value;
    var style = document.getElementById("womenStyle").value;
    var community = document.getElementById('womenCommunity').value;
    var headCovering = document.getElementById('womenHeadCovering').value;
    var device = document.getElementById('womenDevice').value;
    var seeking = document.getElementById("womenSeeking").value;

    // בדיקת שדות ריקים
    if (!status || !firstName || !lastName || !age || !height || !location || !style ) {
        alert(' אנא מלא את כל השדות הנדרשים: סטטוס, סגנון, מיקום, גיל, גובה שם ומשפחה.');
        return; // עצור את הפעולה אם אחד השדות ריק
    }
    if (!/^\d+$/.test(height)) {
        alert(' הכנס גובה בפורמט תקין, לדוגמה: 156');
        return;
    }

    var data = {
        status: status,
        firstName: firstName,
        lastName: lastName,
        age: age,
        height: height,
        location: location,
        style: style,
        community: community,
        headCovering: headCovering,
        device: device,
        seeking: seeking
    };

    fetch('http://localhost:8080/api/women/saveWomen', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('Network response was not ok');
        }
        return response.json();
    })
    .then(data => {
        console.log('Data saved successfully:', data);

     idWomen = data.id;
     document.getElementById('idPerson').value = idWomen;
     console.log(idWomen);
     resetFormFields();

     currentGender = "women";
     console.log(currentGender);
     openPreferencesModal();
    })
    .catch(error => {
        console.error('There was an error!', error);
    });
}

function openPreferencesModal() {
    document.getElementById('preferencesModal').style.display = 'block';
}

function savePreferencesData() {
    var preferredAgeRange = document.getElementById('preferredAgeRange').value.trim();
    var preferredHeightRange = document.getElementById('preferredHeightRange').value.trim();

     if (!/^\d+\s*-\s*\d+$/.test(preferredAgeRange)) {
        alert('הכנס טווח גילאים בפורמט תקין (לדוגמה: 20-30)');
        return;
    }

    if (!/^\d+\s*-\s*\d+$/.test(preferredHeightRange)) {
        alert('הכנס טווח גובה בפורמט תקין (לדוגמה: 160-180)');
        return;
    }

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
    //var preferredAgeRange = document.getElementById('preferredAgeRange').value;
        //var [preferredAgeFrom, preferredAgeTo] = preferredAgeRange.split('-').map(age => age.trim());
    //var preferredHeightRange = document.getElementById('preferredHeightRange').value;
        //var [preferredHeightFrom, preferredHeightTo] = preferredHeightRange.split('-').map(height => height.trim());
    var preferredRegion = document.getElementById('preferredRegion').value;
    var preferredCommunity = document.getElementById('preferredCommunity').value;
    var handkerchiefOrWig = document.getElementById('handkerchiefOrWig').value;
    var preferredStyle = document.getElementById('preferredStyle').value;
    var kosherOrNonKosherDevice = document.getElementById('kosherOrNonKosherDevice').value;
    var preferredStatus = document.getElementById('preferredStatus').value;

    var preferencesData;
        if (currentGender === 'men') {
            preferencesData = {
                men: { id: idMen }, // ה-ID של MEN ישמש גם כמפתח ראשי ב-preferences_men
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
        } else {
            preferencesData = {
                women: { id: idWomen }, // ה-ID של WOMEN ישמש גם כמפתח ראשי ב-preferences_women
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
        }
    console.log('Preferences to be sent:', preferencesData);

    let url = currentGender === 'men' ? `http://localhost:8080/api/men/savePreferences` : `http://localhost:8080/api/women/savePreferences`;
    fetch(url, {

    //fetch('http://localhost:8080/api/men/savePreferences', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(preferencesData),
    })
    .then(response => response.json())
    .then(data => {
        if (data.success) {
            console.log('Preferences data saved successfully:', preferencesData);
            resetFormFields();
            closeModal('preferencesModal'); // סגירת החלונית לאחר שמירת ההעדפות
        } else {
            console.error('Error saving preferences data:', data.message, preferencesData);
        }
    })
    .catch(error => {
        console.error('Fetch error:', error);
    });
}

function resetFormFields() {
    document.getElementById("menStatus").value = "";
    document.getElementById("menFirstName").value = "";
    document.getElementById("menLastName").value = "";
    document.getElementById("menAge").value = "";
    document.getElementById("menHeight").value = "";
    document.getElementById("menLocation").value = "";
    document.getElementById("menStyle").value = "";
    document.getElementById('menCommunity').value = '';
    document.getElementById('menHeadCovering').value = '';
    document.getElementById('menDevice').value = '';
    document.getElementById("menSeeking").value = "";

    document.getElementById("preferredRegion").value = "";
    document.getElementById("preferredCommunity").value = "";
    document.getElementById("handkerchiefOrWig").value = "";
    document.getElementById("preferredStyle").value = "";
    document.getElementById("kosherOrNonKosherDevice").value = "";
    document.getElementById("preferredStatus").value = "";
    document.getElementById("preferredAgeRange").value = "";
    document.getElementById("preferredHeightRange").value = "";


    document.getElementById("womenStatus").value = "";
    document.getElementById("womenFirstName").value = "";
    document.getElementById("womenLastName").value = "";
    document.getElementById("womenAge").value = "";
    document.getElementById("womenHeight").value = "";
    document.getElementById("womenLocation").value = "";
    document.getElementById("womenStyle").value = "";
    document.getElementById("womenSeeking").value = "";
    document.getElementById('womenCommunity').value = '';
    document.getElementById('womenHeadCovering').value = '';
    document.getElementById('womenDevice').value = '';
}