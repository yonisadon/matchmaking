
class UpdateService {
//    static openUpdateModal(data) {
//        document.getElementById('preferredRegion').value = data.preferredRegion || '';
//        // שאר הלוגיקה לפתיחת המודל
//    }
//
//    static saveUpdateData() {
//        // לוגיקת שמירה וולידציה
//    }
//}

const criteriaOptions = {
    status: [
        { value: 'רווק/ה', text: 'רווק/ה' },
        { value: 'married', text: 'נשוי' }
    ],
    style: [
        { value: 'modern', text: 'מודרני' },
        { value: 'traditional', text: 'מסורתי' }
    ]
};

function handleCriteriaChange() {
    console.log('handleCriteriaChange called');
    const criteria = document.getElementById("searchCriteria").value; // שינוי מ-searchCriteria ל-criteria
    const inputContainer = document.getElementById("inputContainer");

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

}