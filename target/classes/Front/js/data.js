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

    var preferredRegion = document.getElementById('preferredRegion').value;
    var preferredCommunity = document.getElementById('preferredCommunity').value;
    var handkerchiefOrWig = document.getElementById('handkerchiefOrWig').value;
    var smokerOrNonSmoker = document.getElementById('smokerOrNonSmoker').value;
    var kosherOrNonKosherDevice = document.getElementById('kosherOrNonKosherDevice').value;
    var preferredStatus = document.getElementById('preferredStatus').value;

    var preferencesData = {
        men: {
            id: idMen // ה-ID של MEN ישמש גם כמפתח ראשי ב-preferences_men
        },
        preferredRegion: preferredRegion,
        preferredCommunity: preferredCommunity,
        handkerchiefOrWig: handkerchiefOrWig,
        smokerOrNonSmoker: smokerOrNonSmoker,
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
    var seeking = document.getElementById("womenSeeking").value;

    var data = {
        status: status,
        firstName: firstName,
        lastName: lastName,
        age: age,
        height: height,
        location: location,
        style: style,
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
    document.getElementById("menSeeking").value = "";

    document.getElementById("womenStatus").value = "";
    document.getElementById("womenFirstName").value = "";
    document.getElementById("womenLastName").value = "";
    document.getElementById("womenAge").value = "";
    document.getElementById("womenHeight").value = "";
    document.getElementById("womenLocation").value = "";
    document.getElementById("womenStyle").value = "";
    document.getElementById("womenSeeking").value = "";
}
//לחצן חפש
    // שליחת בקשה לשרת עם הפרמטרים הנדרשים
  document.addEventListener('DOMContentLoaded', function() {
      let selectedRecordId = null;
      let selectedGender = null;
      let selectedRecordData = null;

  function search() {
      const searchTerm = document.getElementById('searchInput').value;
      const gender = document.querySelector('input[name="gender"]:checked').value;
      const criteria = document.getElementById('searchCriteria').value;

        console.log('Gender:', gender);
        console.log('Criteria:', criteria);
        console.log('Search Term:', searchTerm);
      let url;
      if (criteria === 'all') {
              url = gender === 'women' ? 'http://localhost:8080/api/women/searchAll' : 'http://localhost:8080/api/men/searchAll';

          //url = gender === 'men' ? 'http://localhost:8080/api/men/searchAllTest' : 'http://localhost:8080/api/women/searchAll';
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
//          .then(data => {
//              const tableBody = document.querySelector('#resultsTable tbody');
//              tableBody.innerHTML = '';
 .then(data => {
                console.log('Data fetched: ', data);
                displayResults(data);
            })
            .catch(error => {
                console.error('Error fetching search results:', error);
            });
    }
     function displayResults(data) {
            const tableBody = document.querySelector('#resultsTable tbody');
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
                          <td>${result.id}</td>
                      `;
                      row.onclick = () => selectRecord(result.id, gender, row);
                      tableBody.appendChild(row);
                  });
              }else {
                           console.log('No results found.');
                       }
          }
//          .catch(error => {
//              console.error('Error fetching search results:', error);
//          });
  //}


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
                };

            // הוספת מחלקת "נבחר" לשורה שנבחרה
            row.classList.add('selected');
        }

  function deleteSelected() {
      if (!selectedRecordId) {
          alert("אנא בחר רשומה למחיקה.");
          return;
      }

      const url = selectedGender === 'men' ? `http://localhost:8080/api/men/${selectedRecordId}` : `http://localhost:8080/api/women/${selectedRecordId}`;

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

  function updateSelected() {
        if (!selectedRecordId) {
            alert("אנא בחר רשומה לעדכון.");
            return;
        }
        console.log("tets");
 const modal = document.getElementById("updateModal");
    modal.style.display = "block";
    // מילוי השדות בטופס לפי הנתונים של הרשומה הנבחרת
    document.getElementById('UpStatus').value = selectedRecordData.status;
    document.getElementById('UpFirstName').value = selectedRecordData.firstName;
    document.getElementById('UpLastName').value = selectedRecordData.lastName;
    document.getElementById('UpAge').value = selectedRecordData.age;
    document.getElementById('UpHeight').value = selectedRecordData.height;
    document.getElementById('UpLocation').value = selectedRecordData.location;

    }
    function saveUpdateData() {

        const updatedData = {
            status: document.getElementById('UpStatus').value,
            firstName: document.getElementById('UpFirstName').value,
            lastName: document.getElementById('UpLastName').value,
            age: document.getElementById('UpAge').value,
            height: document.getElementById('UpHeight').value,
            location: document.getElementById('UpLocation').value,
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
});