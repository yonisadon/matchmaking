//document.addEventListener("DOMContentLoaded", function() {
var modal = document.getElementById("menModal");

var btn = document.getElementById("openModalButton");



function closeModalA(modalId) {
    // סגירת המודל
    document.getElementById(modalId).style.display = "none";
    resetFormField();  // איפוס השדות כשסוגרים את המודל
}

//function closeModal(modalId) {
//var modal = document.getElementById(modalId);
//modal.style.display = "none";
//
//resetFormFields();
//}

window.onclick = function(event) {
    if (event.target == modal) {
        modal.style.display = "none";
    }
}

let idMen;
let idWomen;
var currentGender;

// פונקציה לחישוב גיל
function calculateAge(dateOfBirthId, ageFieldId) {
    const dob = new Date(document.getElementById(dateOfBirthId).value);
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
document.getElementById('dateOfBirthMen').addEventListener('change', function() {
    calculateAge('dateOfBirthMen', 'menAge');
});

// הקשבה לשינויים בתאריך הלידה של הנשים
document.getElementById('dateOfBirthWomen').addEventListener('change', function() {
    calculateAge('dateOfBirthWomen', 'womenAge');
});

// חישוב גיל כאשר הדף נטען מחדש אם יש ערך בתאריך הלידה של הגברים
window.addEventListener('load', function() {
    if (document.getElementById('dateOfBirthMen').value) {
        calculateAge('dateOfBirthMen', 'menAge');
    }

    if (document.getElementById('dateOfBirthWomen').value) {
        calculateAge('dateOfBirthWomen', 'womenAge');
    }
});



// פונקציה לשמירת נתוני MEN
function saveMenData() {

    var status = document.getElementById("menStatus").value;
    var firstName = document.getElementById("menFirstName").value;
    var lastName = document.getElementById("menLastName").value;
    var dateOfBirth = document.getElementById("dateOfBirthMen").value;
    var age = document.getElementById("menAge").value;
    var height = document.getElementById("menHeight").value;
    var location = document.getElementById("menLocation").value;
    var style = document.getElementById("menStyle").value;
    var community = document.getElementById('menCommunity').value;
    var headCovering = document.getElementById('menHeadCovering').value;
    var device = document.getElementById('menDevice').value;
    var phone = document.getElementById('menPhone').value;
    var seeking = document.getElementById("menSeeking").value;
    var work = document.getElementById("menWork").value;
    var studies = document.getElementById("menStudies").value;

 // קבלת התמונות
    var profilePictureUrl = document.getElementById("profilePictureMen").files[0];
    var additionalPictureUrl = document.getElementById("additionalPictureMen").files[0];
console.log(profilePictureUrl);
    // בדיקת שדות ריקים
    if (!status || !firstName || !age || !height || !location || !style ) {
        alert(' אנא מלא את כל השדות הנדרשים: סטטוס, סגנון, מיקום, גיל, גובה שם ומשפחה.');
        return; // עצור את הפעולה אם אחד השדות ריק
    }
  if (!/^\d+$/.test(height)) {
        alert(' הכנס גובה בפורמט תקין, לדוגמה: 156');
        return;
    }

    // יצירת אובייקט FormData לשליחת נתוני הטופס והקבצים
    var menData = new FormData();
    menData.append('status', status);
    menData.append('firstName', firstName);
    menData.append('lastName', lastName);
    menData.append('dateOfBirth', dateOfBirth);
    menData.append('age', age);
    menData.append('height', height);
    menData.append('location', location);
    menData.append('style', style);
    menData.append('community', community);
    menData.append('headCovering', headCovering);
    menData.append('device', device);
    menData.append('phone', phone);
    menData.append('seeking', seeking);
    menData.append('work', work);
    menData.append('studies', studies);

 // הוספת התמונות אם הן קיימות
    if (profilePictureUrl) {
        menData.append('profilePictureUrl', profilePictureUrl);
    }
    if (additionalPictureUrl) {
        menData.append('additionalPictureUrl', additionalPictureUrl);
    }
fetch('http://localhost:8080/api/men/saveMen', {
    method: 'POST',
    body: menData // שליחת FormData ולא JSON
})
.then(response => response.json())
.then(data => {
    if (data.success) {
        console.log('Men data saved successfully:', menData, data);

        idMen = data.id; // שמירת ה-ID שהתקבל מהשרת
        document.getElementById('idPerson').value = idMen; // עדכון השדה בחלונית העדפות
        console.log(idMen);
        resetFormField();

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
//    fetch('http://localhost:8080/api/men/saveMen', {
//        method: 'POST',
////        headers: {
////            'Content-Type': 'application/json'
////        },
//        //body: JSON.stringify(menData)
//    })
//    .then(response => response.json())
//    .then(data => {
//        if (data.success) {
//            console.log('Men data saved successfully:', menData, data);
//
//            idMen = data.id; // שמירת ה-ID שהתקבל מהשרת
//            document.getElementById('idPerson').value = idMen; // עדכון השדה בחלונית העדפות
//            console.log(idMen);
//            resetFormFields();
//
//            currentGender = "men";
//            console.log(currentGender);
//            openPreferencesModal(); // פתיחת החלונית להוספת העדפות
//        } else {
//            console.error('Error saving men data:', data.message);
//        }
//    })
//    .catch(error => {
//        console.error('Error saving men data:', error);
//    });
//}

function saveWomenData() {
    var status = document.getElementById("womenStatus").value;
    var firstName = document.getElementById("womenFirstName").value;
    var lastName = document.getElementById("womenLastName").value;
    var dateOfBirth = document.getElementById("dateOfBirthWomen").value;
    var age = document.getElementById("womenAge").value;
    var height = document.getElementById("womenHeight").value;
    var location = document.getElementById("womenLocation").value;
    var style = document.getElementById("womenStyle").value;
    var community = document.getElementById('womenCommunity').value;
    var headCovering = document.getElementById('womenHeadCovering').value;
    var device = document.getElementById('womenDevice').value;
    var phone = document.getElementById('womenPhone').value;
    var seeking = document.getElementById("womenSeeking").value;
    var work = document.getElementById("womenWork").value;
    var studies = document.getElementById("womenStudies").value;
    var profilePictureUrl = document.getElementById("profilePictureWomen").files[0];
    var additionalPictureUrl = document.getElementById("additionalPictureWomen").files[0];
    // בדיקת שדות ריקים
    if (!status || !firstName || !age || !height || !location || !style ) {
        alert(' אנא מלא את כל השדות הנדרשים: סטטוס, סגנון, מיקום, גיל, גובה שם ומשפחה.');
        return; // עצור את הפעולה אם אחד השדות ריק
    }
    if (!/^\d+$/.test(height)) {
        alert(' הכנס גובה בפורמט תקין, לדוגמה: 156');
        return;
    }

    var womenData = new FormData();
    womenData.append('status', status);
    womenData.append('firstName', firstName);
    womenData.append('lastName', lastName);
    womenData.append('dateOfBirth', dateOfBirth);
    womenData.append('age', age);
    womenData.append('height', height);
    womenData.append('location', location);
    womenData.append('style', style);
    womenData.append('community', community);
    womenData.append('headCovering', headCovering);
    womenData.append('device', device);
    womenData.append('phone', phone);
    womenData.append('seeking', seeking);
    womenData.append('work', work);
    womenData.append('studies', studies);

 // הוספת התמונות אם הן קיימות
    if (profilePictureUrl) {
        womenData.append('profilePictureUrl', profilePictureUrl);
    }
    if (additionalPictureUrl) {
        womenData.append('additionalPictureUrl', additionalPictureUrl);
    }
    fetch('http://localhost:8080/api/women/saveWomen', {
        method: 'POST',
        body: womenData
    })
.then(response => response.json())
.then(data => {
    if (data.success) {
        console.log('women data saved successfully:', womenData, data);
     idWomen = data.id;
     document.getElementById('idPerson').value = idWomen;
     console.log(idWomen);
     resetFormField();

     currentGender = "women";
     console.log(currentGender);
     openPreferencesModal();
    } else {
        console.error('Error saving women data:', data.message);
    }
})
.catch(error => {
    console.error('Error saving men data:', error);
});
}

function openPreferencesModal() {
    document.getElementById('preferencesModal').style.display = 'block';
}

function savePreferencesData() {
    var preferredAgeFrom = parseInt(document.getElementById("preferredAgeFrom").value.trim());
    var preferredAgeTo = parseInt(document.getElementById("preferredAgeTo").value.trim());
    var preferredHeightFrom = parseInt(document.getElementById("preferredHeightFrom").value.trim());
    var preferredHeightTo = parseInt(document.getElementById("preferredHeightTo").value.trim());

    // בדיקת חובה והאם הערכים תקינים עבור טווח גילאים
    if (isNaN(preferredAgeFrom) || isNaN(preferredAgeTo) || preferredAgeFrom > preferredAgeTo) {
        alert('אנא הכנס טווח גילאים תקין');
        return;
    }

    // בדיקת חובה והאם הערכים תקינים עבור טווח גובה
    if (isNaN(preferredHeightFrom) || isNaN(preferredHeightTo) || preferredHeightFrom > preferredHeightTo) {
        alert('אנא הכנס טווח גובה תקין');
        return;
    }

    // איחוד השדות לפורמט מתאים עבור שליחה לבסיס הנתונים
//    let preferredAgeRange = `${preferredAgeFrom}-${preferredAgeTo}`;
//    let preferredHeightRange = `${preferredHeightFrom}-${preferredHeightTo}`;

//    var preferredAgeRange = document.getElementById('preferredAgeRange').value.trim();
//    var preferredHeightRange = document.getElementById('preferredHeightRange').value.trim();

    //if(preferredAgeRange){
//         if (!/^\d+\s*-\s*\d+$/.test(preferredAgeRange)) {
//             alert('הכנס טווח גילאים בפורמט תקין (לדוגמה: 20-30)');
//             return;
//         }
//    //}
//    //if(preferredHeightRange){
//         if (!/^\d+\s*-\s*\d+$/.test(preferredHeightRange)) {
//             alert('הכנס טווח גובה בפורמט תקין (לדוגמה: 160-180)');
//                return;
//        }
    //}

//    var [preferredAgeFrom, preferredAgeTo] = preferredAgeRange.split('-').map(age => parseInt(age.trim()));
//    var [preferredHeightFrom, preferredHeightTo] = preferredHeightRange.split('-').map(height => parseInt(height.trim()));

//    if (preferredAgeFrom > preferredAgeTo) {
//        alert('הגיל ההתחלתי לא יכול להיות גדול מהגיל הסופי');
//        return;
//    }
//
//    if (preferredHeightFrom > preferredHeightTo) {
//        alert('הגובה ההתחלתי לא יכול להיות גדול מהגובה הסופי');
//        return;
//    }
    //var preferredAgeRange = document.getElementById('preferredAgeRange').value;
        //var [preferredAgeFrom, preferredAgeTo] = preferredAgeRange.split('-').map(age => age.trim());
    //var preferredHeightRange = document.getElementById('preferredHeightRange').value;
        //var [preferredHeightFrom, preferredHeightTo] = preferredHeightRange.split('-').map(height => height.trim());
    var preferredRegion = document.getElementById('preferredRegion').value;
    var preferredCommunity = document.getElementById('preferredCommunity').value;
    var handkerchiefOrWig = document.getElementById('handkerchiefOrWig').value;

    var kosherOrNonKosherDevice = document.getElementById('kosherOrNonKosherDevice').value;
    var preferredStatus = document.getElementById('preferredStatus').value;
    var preferredWork = document.getElementById('preferredWork').value;
    var preferredStyle = document.getElementById('preferredStyle').value;
    var preferredStudies = document.getElementById('preferredStudies').value;

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
                kosherOrNonKosherDevice: kosherOrNonKosherDevice,
                preferredStatus: preferredStatus,
                preferredStyle: preferredStyle,
                preferredWork: preferredWork,
                preferredStudies: preferredStudies
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
                kosherOrNonKosherDevice: kosherOrNonKosherDevice,
                preferredStatus: preferredStatus,
                preferredStyle: preferredStyle,
                preferredWork: preferredWork,
                preferredStudies: preferredStudies
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
            resetFormField();
            closeModalA('preferencesModal'); // סגירת החלונית לאחר שמירת ההעדפות
        } else {
            console.error('Error saving preferences data:', data.message, preferencesData);
        }
    })
    .catch(error => {
        console.error('Fetch error:', error);
    });
}

function resetFormField() {
    console.log("Resetting form fields...");

    document.getElementById("menStatus").value = "";
    document.getElementById("menFirstName").value = "";
    document.getElementById("menLastName").value = "";
    document.getElementById("dateOfBirthMen").value = "";
    document.getElementById("menAge").value = "";
    document.getElementById("menHeight").value = "";
    document.getElementById("menLocation").value = "";
    document.getElementById("menStyle").value = "";
    document.getElementById('menCommunity').value = '';
    document.getElementById('menHeadCovering').value = '';
    document.getElementById('menDevice').value = '';
    document.getElementById("menSeeking").value = "";
    document.getElementById("menPhone").value = "";
    document.getElementById("menWork").value = "";
    document.getElementById("menStudies").value = "";
    document.getElementById("profilePictureMen").value = "";
    document.getElementById("additionalPictureMen").value = "";

    document.getElementById("preferredRegion").value = "";
    document.getElementById("preferredCommunity").value = "";
    document.getElementById("handkerchiefOrWig").value = "";
    document.getElementById("preferredStyle").value = "";
    document.getElementById("kosherOrNonKosherDevice").value = "";
    document.getElementById("preferredStatus").value = "";
//    document.getElementById("preferredAgeRange").value = "";
//    document.getElementById("preferredHeightRange").value = "";
    document.getElementById("preferredWork").value = "";
    document.getElementById("preferredStudies").value = "";

    document.getElementById("preferredAgeFrom").value = "";
    document.getElementById("preferredAgeTo").value = "";
    document.getElementById("preferredHeightFrom").value = "";
    document.getElementById("preferredHeightTo").value = "";

    document.getElementById("womenStatus").value = "";
    document.getElementById("womenFirstName").value = "";
    document.getElementById("womenLastName").value = "";
    document.getElementById("dateOfBirthWomen").value = "";
    document.getElementById("womenAge").value = "";
    document.getElementById("womenHeight").value = "";
    document.getElementById("womenLocation").value = "";
    document.getElementById("womenStyle").value = "";
    document.getElementById("womenSeeking").value = "";
    document.getElementById('womenCommunity').value = '';
    document.getElementById('womenHeadCovering').value = '';
    document.getElementById('womenDevice').value = '';
    document.getElementById("womenPhone").value = "";
    document.getElementById('womenWork').value = '';
    document.getElementById("womenStudies").value = "";
    document.getElementById("profilePictureWomen").value = "";
    document.getElementById("additionalPictureWomen").value = "";
}

//});