const addButton = document.getElementById('add-button');
const showButton = document.getElementById('show-button');
const updateButton = document.getElementById('update-button');
const componentList = document.getElementById('component-list');
const dialogContainer = document.getElementById('dialog-container');
const componentNameInput = document.getElementById('component-name');
const componentManufacturerInput = document.getElementById('component-manufacturer');
const componentPriceInput = document.getElementById('component-price');
const dialogSubmitButton = document.getElementById('dialog-submit');
const dialogCancelButton = document.getElementById('dialog-cancel');
/**
 * The database instance of the Components database.
 *
 *  @type {IDBDatabase}
 */
let db;

// Open IndexedDB connection
const openDB = () => {
    const dbName = 'computerComponents';
    const request = indexedDB.open(dbName, 1.2);

    request.onerror = (event) => {
        console.error('Error opening database:', event.error);
    };

    request.onsuccess = (event) => {
        db = event.target.result;
        console.log('Database opened successfully');
    };

    request.onupgradeneeded = (event) => {
        const objectStore = event.target?.result.createObjectStore('components', {
            keyPath: ['name', 'manufacturer'], // Composite key
        });
    };
};

openDB();

/**
 *
 * @param {string} nameToDelete
 * @param {string} manufacturerToDelete
 */
function deleteComponent(nameToDelete, manufacturerToDelete) {
    const transaction = db.transaction('components', 'readwrite');
    const objectStore = transaction.objectStore('components');
    const request = objectStore.delete([nameToDelete, manufacturerToDelete]);

    request.onsuccess = (event) => {
        console.log('Component deleted successfully');
        showButton?.click(); // Refresh the component list
    };

    request.onerror = (event) => {
        console.error('Error deleting component:', event.error);
        alert('Failed to delete component.');
    };
}

function addComponent(name, manufacturer, price) {
    const transaction = db.transaction('components', 'readwrite');
    const objectStore = transaction.objectStore('components');
    // const request = objectStore.put({
    //     name: name,
    //     manufacturer: manufacturer,
    //     price: price
    // });

    const getRequest = objectStore.get([name, manufacturer]);
    getRequest.onsuccess = (event) => {
        if (event.target.result) {
            alert('Component with the same name and manufacturer already exists.');
        } else {
            // Add new component if it doesn't exist
            const putRequest = objectStore.put({ name, manufacturer, price });
            putRequest.onsuccess = (event) => {
                console.log('Component added successfully');
                console.log('Component added successfully');
                dialogContainer.style.display = 'none';
                componentNameInput.value = ''; // Clear input fields
                componentManufacturerInput.value = '';
                componentPriceInput.value = '';
                showButton?.click();
            };
            putRequest.onerror = (event) => {
                console.error('Error adding component:', event.error);
                alert('Failed to add component. Please try again.');
            };
        }
    };

    // request.onsuccess = (event) => {
    //     console.log(event)
    //     console.log('Component added successfully');
    //     dialogContainer.style.display = 'none';
    //     componentNameInput.value = ''; // Clear input fields
    //     componentManufacturerInput.value = '';
    //     componentPriceInput.value = '';
    //     showButton?.click(); // Refresh the component list
    // };
    //
    // request.onerror = (event) => {
    //     console.error('Error adding component:', event.error);
    //     alert('Failed to add component. Please try again.');
    // };
}

/*
* Helper function to display components in the list
*/
const displayComponents = (components) => {
    componentList.innerHTML = ''; // Clear the component list element

    // Create a table element
    const table = document.createElement('table');
    table.style.border = '1px'; // Add a border for better visualization (optional)

    // Create table header row
    const headerRow = document.createElement('tr');
    headerRow.innerHTML = `<th>Name</th><th>Manufacturer</th><th>Price</th><th>Delete</th>`;
    table.appendChild(headerRow);

    // Add data rows for each component
    components.forEach((component) => {
        const dataRow = document.createElement('tr');

        const nameData = document.createElement('td')
        nameData.innerText = component.name

        const manufacturerData = document.createElement('td')
        manufacturerData.innerText = component.manufacturer

        const priceData = document.createElement('td')
        priceData.innerText = component.price

        const deleteButton = document.createElement("button")
        deleteButton.innerText = "delete"
        deleteButton.addEventListener("click", function () {
            deleteComponent(component.name, component.manufacturer)
        })

        dataRow.appendChild(nameData);
        dataRow.appendChild(manufacturerData)
        dataRow.appendChild(priceData)
        dataRow.appendChild(deleteButton)

        table.appendChild(dataRow);
    });

    componentList.appendChild(table); // Add the table to the component list element
};

// Show components button click handler
showButton.addEventListener('click', () => {
    const transaction = db.transaction('components', 'readonly');
    const objectStore = transaction.objectStore('components');
    const request = objectStore.getAll();

    request.onsuccess = (event) => {
        console.log(event.target.result)
        displayComponents(event.target.result);
    };
});

// Update button click handler
updateButton.addEventListener('click', () => {
    // Implement functionality to get component details for update and call update function
    // Example: Get component details from a form or selection
    const nameToUpdate = 'Component Name'; // Replace with actual component name
    const manufacturerToUpdate = 'Manufacturer Name'; // Replace with actual manufacturer

    const transaction = db.transaction('components', 'readwrite');
    const objectStore = transaction.objectStore('components');
    const request = objectStore.get([nameToUpdate, manufacturerToUpdate]);

    request.onsuccess = (event) => {
        if (event.target.result) {
            const updatedPrice = 'New Price'; // Replace with updated price
            event.target.result.price = updatedPrice;
            objectStore.put(event.target.result);
            console.log('Component updated successfully');
            showButton.click(); // Refresh the component list
        } else {
            console.error('Component not found for update');
            alert('Component not found.');
        }
    };
});

// Add button submit button click handler
dialogSubmitButton.addEventListener('click', () => {
    const name = componentNameInput.value;
    const manufacturer = componentManufacturerInput.value;
    const price = componentPriceInput.value;
    if (name && manufacturer && price) {
        addComponent(name, manufacturer, price)
    } else {
        alert('Please enter a valid name, manufacturer and price.');
    }
});

// Dialog cancel button click handler
dialogCancelButton.addEventListener('click', () => {
    dialogContainer.style.display = 'none';
});

// Add button click handler
addButton.addEventListener('click', () => {
    dialogContainer.style.display = 'block';
    componentNameInput.value = '';
    componentManufacturerInput.value = '';
    componentPriceInput.value = '';
});