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

//function openPreferencesModal() {
//    const preferencesModal = document.getElementById('preferencesModal');
//    preferencesModal.style.display = 'block';
//}

//let preferencesData = {};
//
//// פונקציה לשמירת העדפות לגבר במשתנה זמני
//function savePreferencesData() {
//    const preferredRegion = document.getElementById('preferredRegion').value;
//    const preferredCommunity = document.getElementById('preferredCommunity').value;
//    const handkerchiefOrWig = document.getElementById('handkerchiefOrWig').value;
//    const smokerOrNonSmoker = document.getElementById('smokerOrNonSmoker').value;
//    const kosherOrNonKosherDevice = document.getElementById('kosherOrNonKosherDevice').value;
//    const preferredStatus = document.getElementById('preferredStatus').value;
////    preferencesData = {
////        preferredRegion: preferredRegion,
////        preferredCommunity: preferredCommunity,
////        handkerchiefOrWig: handkerchiefOrWig,
////        smokerOrNonSmoker: smokerOrNonSmoker,
////        kosherOrNonKosherDevice: kosherOrNonKosherDevice,
////        preferredStatus: preferredStatus
////    };
//        preferencesData = {
//            preferredRegion: "11",
//            preferredCommunity: "11",
//            handkerchiefOrWig: "11",
//            smokerOrNonSmoker: "11",
//            kosherOrNonKosherDevice: "11",
//            preferredStatus: "11"
//        };
//
//    console.log('Preferences to be sent:', preferencesData);
//    closeModal('preferencesModal');
//}
//
//
//function saveMenData() {
//    var status = document.getElementById("menStatus").value;
//    var firstName = document.getElementById("menFirstName").value;
//    var lastName = document.getElementById("menLastName").value;
//    var age = document.getElementById("menAge").value;
//    var height = document.getElementById("menHeight").value;
//    var location = document.getElementById("menLocation").value;
//    var style = document.getElementById("menStyle").value;
//    var seeking = document.getElementById("menSeeking").value;
//
//    // בדיקת תקינות
//    if (!status) {
//        alert("שדה 'סטטוס' הוא חובה.");
//        return;
//    }
//    if (!age) {
//        alert("שדה 'גיל' הוא חובה.");
//        return;
//    }
//    if (!height) {
//        alert("שדה 'גובה' הוא חובה.");
//        return;
//    }
//
//    var menData = {
//        status: status,
//        firstName: firstName,
//        lastName: lastName,
//        age: age,
//        height: height,
//        location: location,
//        style: style,
//        seeking: seeking
//    };
//
//    fetch('http://localhost:8080/api/men/saveMen', {
//        method: 'POST',
//        headers: {
//            'Content-Type': 'application/json'
//        },
//        body: JSON.stringify(menData)
//    })
//    .then(response => response.json())
//    .then(data => {
//        if (data.success) {
//            console.log('Men data saved successfully:', menData, data);
//            const menId = data.id;
//
//            const preferencesWithMenId = {
//                menId: menId,
//                ...preferencesData
//            };
//
//            fetch('http://localhost:8080/api/men/savePreferences', {
//                method: 'POST',
//                headers: {
//                    'Content-Type': 'application/json',
//                },
//                body: JSON.stringify(preferencesWithMenId),
//            })
//            .then(response => {
//                if (!response.ok) {
//                    throw new Error('Network response was not ok');
//                }
//                return response.json();
//            })
//            .then(data => {
//                if (data.success) {
//                    console.log('Preferences data saved successfully:', preferencesWithMenId);
//                    closeModal('menModal');
//                    preferencesData = {}; // איפוס המשתנה הזמני לאחר השמירה
//                } else {
//                    console.error('Error saving preferences data:', data.message);
//                }
//            })
//            .catch(error => {
//                console.error('Fetch error:', error);
//            });
//
//
//
//            resetFormFields(); // איפוס השדות בטופס
//        } else {
//            console.error('Error saving men data:', data.message);
//        }
//    })
//    .catch(error => {
//        console.error('Error saving men data:', error);
//    });
//}

let idMen; // משתנה לאחסון ה-ID של MEN

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

    // בדיקת תקינות
    if (!status) {
        alert("שדה 'סטטוס' הוא חובה.");
        return;
    }
    if (!age) {
        alert("שדה 'גיל' הוא חובה.");
        return;
    }
    if (!height) {
        alert("שדה 'גובה' הוא חובה.");
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
            document.getElementById('idMen').value = idMen; // עדכון השדה בחלונית העדפות
            console.log(idMen);
            resetFormFields();
            openPreferencesModal(); // פתיחת החלונית להוספת העדפות
        } else {
            console.error('Error saving men data:', data.message);
        }
    })
    .catch(error => {
        console.error('Error saving men data:', error);
    });
}

// פונקציה לפתיחת החלונית להוספת העדפות
function openPreferencesModal() {
    document.getElementById('preferencesModal').style.display = 'block';
}

// פונקציה לשמירת העדפות לגבר במשתנה זמני ולאחר מכן למסד הנתונים
// פונקציה לשמירת העדפות לגבר במשתנה זמני ולאחר מכן למסד הנתונים
function savePreferencesData() {
    console.log('men data3:', idMen);

    var preferredAgeRange = document.getElementById('preferredAgeRange').value;
        var [preferredAgeFrom, preferredAgeTo] = preferredAgeRange.split('-').map(age => age.trim());
    var preferredHeightRange = document.getElementById('preferredHeightRange').value;
        var [preferredHeightFrom, preferredHeightTo] = preferredHeightRange.split('-').map(height => height.trim());
    var preferredRegion = document.getElementById('preferredRegion').value;
    var preferredCommunity = document.getElementById('preferredCommunity').value;
    var handkerchiefOrWig = document.getElementById('handkerchiefOrWig').value;
    var preferredStyle = document.getElementById('preferredStyle').value;
    var kosherOrNonKosherDevice = document.getElementById('kosherOrNonKosherDevice').value;
    var preferredStatus = document.getElementById('preferredStatus').value;

    var preferencesData = {
        men: {
            id: idMen // ה-ID של MEN ישמש גם כמפתח ראשי ב-preferences_men
        },
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

    console.log('Preferences to be sent:', preferencesData);

    fetch('http://localhost:8080/api/men/savePreferences', {
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

    fetch('http://localhost:8080/api/women/saveWomenData', {
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
        resetFormFields();
        console.log('Data saved successfully:', data);
    })
    .catch(error => {
        console.error('There was an error!', error);
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