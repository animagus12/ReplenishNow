let productID = 0;

function addProduct() {
    // Auto-increment ProductID
    productID++;

    // Collecting the input values
    const productName = document.getElementById('productName').value;
    const price = document.getElementById('price').value;
    const stockCount = document.getElementById('stockCount').value;
    const description = document.getElementById('description').value;
    const category = document.getElementById('category').value;
    const deliveryFrequency = document.getElementById('deliveryFrequency').value;

    // Here you would typically send the data to your backend server
    console.log({
        productID,
        productName,
        price,
        stockCount,
        description,
        category,
        deliveryFrequency
    });

    // Clear the form inputs
    document.getElementById('addProductForm').reset();

    // Close the modal
    const addProductModal = new bootstrap.Modal(document.getElementById('addProductModal'));
    addProductModal.hide();
}

//function editRow(button) {
//    var row = button.parentNode.parentNode;
//
//    // Toggle edit mode
//    for (var i = 1; i < row.cells.length - 2; i++) {
//        var cell = row.cells[i];
//        if (cell.querySelector('input')) {
//            // If already in edit mode, save the changes
//            var input = cell.querySelector('input');
//            cell.innerHTML = "<h6>" + input.value + "</h6>";
//        } else {
//            // Enter edit mode
//            var currentValue = cell.innerText;
//            cell.innerHTML = "<input class='edit-mode' type='text' value='" + currentValue + "' />";
//        }
//    }
//}

function editRowProd(button) {
    // Get the row to be edited
    var row = button.parentNode.parentNode;

    // Toggle edit mode for each cell except the last two (edit and delete buttons)
    for (var i = 1; i < row.cells.length - 2; i++) {
        var cell = row.cells[i];
        if (cell.querySelector('input') || cell.querySelector('select')) {
            // If already in edit mode, save the changes
            if (i === 4) {
                var select = cell.querySelector('select');
                var selectedCategory = select.options[select.selectedIndex].text;
                cell.innerHTML = "<div class='badge badge-opacity-warning'>" + selectedCategory + "</div>";
            } else {
                var input = cell.querySelector('input');
                cell.innerHTML = "<h6>" + input.value + "</h6>";
            }
        } else {
            // Enter edit mode
            if (i === 4) {
                // Replace the "Category" cell with a dropdown
                var currentCategory = cell.innerText.trim();
                cell.innerHTML = `
                    <select class="edit-mode">
                        <option value="Perishables" ${currentCategory === 'Perishables' ? 'selected' : ''}>Perishables</option>
                        <option value="Liquor" ${currentCategory === 'Liquor' ? 'selected' : ''}>Liquor</option>
                        <option value="Preservable" ${currentCategory === 'Preservable' ? 'selected' : ''}>Preservable</option>
                        <option value="Snacks" ${currentCategory === 'Snacks' ? 'selected' : ''}>Snacks</option>
                    </select>`;
            } else {
                var currentValue = cell.innerText;
                cell.innerHTML = "<input class='edit-mode' type='text' value='" + currentValue + "' />";
            }
        }
    }
}

function editRowSub(button) {
    var row = button.parentNode.parentNode;

    for (var i = 0; i < row.cells.length - 2; i++) {
        var cell = row.cells[i];
        if (cell.querySelector('input') || cell.querySelector('select')) {
            // Save the changes
            if (i === 2) {
                var select = cell.querySelector('select');
                var selectedCategory = select.options[select.selectedIndex].text;
                cell.innerHTML = "<div class='badge badge-opacity-warning'>" + selectedCategory + "</div>";
            } else if (i === 3) {
                var select = cell.querySelector('select');
                var selectedStatus = select.options[select.selectedIndex].text;
                cell.innerHTML = "<a href=''><div class='badge badge-opacity-" + (selectedStatus === 'Active' ? 'success' : 'danger') + "'>" + selectedStatus + "</div></a>";
            } else {
                var input = cell.querySelector('input');
                cell.innerHTML = "<h6>" + input.value + "</h6>";
            }
        } else {
            // Enter edit mode
            if (i === 2) {
                // Category column with a dropdown
                var currentCategory = cell.innerText.trim();
                cell.innerHTML = `
                    <select class="edit-mode">
                        <option value="Breakfast" ${currentCategory === 'Breakfast' ? 'selected' : ''}>Breakfast</option>
                        <option value="Furniture" ${currentCategory === 'Furniture' ? 'selected' : ''}>Furniture</option>
                        <option value="Daily Essentials" ${currentCategory === 'Daily Essentials' ? 'selected' : ''}>Daily Essentials</option>
                        <option value="Baby Care" ${currentCategory === 'Baby Care' ? 'selected' : ''}>Baby Care</option>
                        <option value="DIY" ${currentCategory === 'DIY' ? 'selected' : ''}>DIY</option>
                    </select>`;
            } else if (i === 3) {
                // Status column with a dropdown
                var currentStatus = cell.innerText.trim();
                cell.innerHTML = `
                    <select class="edit-mode">
                        <option value="Active" ${currentStatus === 'Active' ? 'selected' : ''}>Active</option>
                        <option value="Inactive" ${currentStatus === 'Inactive' ? 'selected' : ''}>Inactive</option>
                    </select>`;
            } else {
                var currentValue = cell.innerText.trim();
                cell.innerHTML = "<input class='edit-mode' type='text' value='" + currentValue + "' />";
            }
        }
    }
}

function deleteRow(button) {
    // Get the row to be deleted
    var row = button.parentNode.parentNode;

    // Confirm the deletion with the user
    var confirmDeletion = confirm("Are you sure you want to delete this item?");

    // If confirmed, delete the row
    if (confirmDeletion) {
        row.parentNode.removeChild(row);
    }
}

function confirmDelivery(button) {
    var row = button.parentNode.parentNode;
    var customerName = row.cells[1].innerText.trim();
    var product = row.cells[2].innerText.trim();

    var confirmAction = confirm(`Are you sure you want to confirm delivery for ${customerName}'s order of ${product}?`);

    if (confirmAction) {
        button.className = 'badge badge-opacity-success';
        button.innerHTML = '<i class="ti-check-box"></i>';
        alert('Delivery confirmed!');
    } else {
        alert('Delivery not confirmed.');
    }
}

function toggleProducts(row) {
    // Find the next row which contains the product list
    const nextRow = row.nextElementSibling;
    if (nextRow && nextRow.classList.contains('product-list')) {
        // Toggle the display of the product list
        nextRow.style.display = nextRow.style.display === 'table-row' ? 'none' : 'table-row';
    }
}

// Sample products list
const products = [
    { name: 'Product A', price: 200 },
    { name: 'Product B', price: 300 },
    { name: 'Product C', price: 150 },
    { name: 'Product D', price: 500 },
    { name: 'Product E', price: 700 },
];

document.getElementById('add-subscription-btn').addEventListener('click', function () {
    // Add a new row with input fields to the table
    const tbody = document.getElementById('subscription-table-body');
    const newRow = document.createElement('tr');

    newRow.innerHTML = `
            <td><input type="text" class="form-control" placeholder="Subscription Name" id="subscriptionName"></td>
            <td><input type="number" class="form-control" placeholder="Price" id="subscriptionPrice"></td>
            <td><input type="text" class="form-control" placeholder="Category" id="subscriptionCategory"></td>
            <td>
                <select class="form-control" id="subscriptionStatus">
                    <option value="Active">Active</option>
                    <option value="Inactive">Inactive</option>
                </select>
            </td>
            <td><input type="number" class="form-control" placeholder="Total Subbed" id="totalSubbed"></td>
            <td colspan="2">
                <button class="btn btn-success btn-sm" onclick="saveSubscription(this)">Save</button>
                <button class="btn btn-danger btn-sm" onclick="cancelSubscription(this)">Cancel</button>
            </td>
        `;

    // Append a row for the product selection
    const productRow = document.createElement('tr');
    productRow.classList.add('hidden-input');

    productRow.innerHTML = `
            <td colspan="7">
                <div class="form-group">
                    <label for="productSelect">Add Products:</label>
                    <select id="productSelect" class="form-control">
                        ${products.map(product => `<option value="${product.name}-${product.price}">${product.name} - ₹${product.price}</option>`).join('')}
                    </select>
                </div>
                <ul id="selectedProductsList"></ul>
                <button class="btn btn-primary btn-sm" type="button" onclick="addProduct()">Add Product</button>
            </td>
        `;

    tbody.appendChild(newRow);
    tbody.appendChild(productRow);
});

function saveSubscription(button) {
    const row = button.closest('tr');
    const productRow = row.nextElementSibling;

    const name = document.getElementById('subscriptionName').value;
    const price = document.getElementById('subscriptionPrice').value;
    const category = document.getElementById('subscriptionCategory').value;
    const status = document.getElementById('subscriptionStatus').value;
    const totalSubbed = document.getElementById('totalSubbed').value;

    if (!name || !price || !category || !status || !totalSubbed) {
        alert('Please fill in all fields.');
        return;
    }

    row.innerHTML = `
            <td><div class="d-flex"><div><h6>${name}</h6></div></div></td>
            <td><h6>₹${price}</h6></td>
            <td><div class="badge badge-opacity-warning">${category}</div></td>
            <td><a href=""><div class="badge badge-opacity-${status === 'Active' ? 'success' : 'danger'}">${status}</div></a></td>
            <td><h6>${totalSubbed}</h6></td>
            <td><div class="badge badge-success" onclick="editRowSub(this)"><i class="ti-pencil"></i></div></td>
            <td><div class="badge badge-opacity-danger" onclick="deleteRow(this)"><i class="ti-trash"></i></div></td>
        `;

    productRow.classList.remove('hidden-input');
}


function toggleProducts(row) {
    const nextRow = row.nextElementSibling;
    if (nextRow && nextRow.classList.contains('product-list')) {
        nextRow.style.display = nextRow.style.display === 'table-row' ? 'none' : 'table-row';
    }
}



document.addEventListener('DOMContentLoaded', function () {
    const addSubscriptionBtn = document.getElementById('add-subscription-btn');

    // Show the modal when the button is clicked
    addSubscriptionBtn.addEventListener('click', function () {
        const addSubscriptionModal = new bootstrap.Modal(document.getElementById('addSubscriptionModal'));
        addSubscriptionModal.show();
    });

    // Handle adding products to the subscription
    document.querySelectorAll('.add-product').forEach(button => {
        button.addEventListener('click', function () {
            const productName = this.getAttribute('data-product-name');
            const productPrice = this.getAttribute('data-product-price');

            const selectedProducts = document.getElementById('selected-products');
            const productItem = document.createElement('li');
            productItem.classList.add('list-group-item', 'd-flex', 'justify-content-between', 'align-items-center');
            productItem.innerHTML = `${productName} - ₹${productPrice}
                          <button type="button" class="badge badge-danger btn-sm remove-product"><i class="ti-trash"></i></button>`;

            selectedProducts.appendChild(productItem);

            // Add event listener to the remove button
            productItem.querySelector('.remove-product').addEventListener('click', function () {
                productItem.remove();
            });
        });
    });

    // Handle form submission (for demonstration purposes, just log the form data)
    document.getElementById('subscriptionForm').addEventListener('submit', function (e) {
        e.preventDefault();
        const formData = new FormData(this);
        console.log('Subscription Name:', formData.get('subscriptionName'));
        console.log('Subscription Description:', formData.get('subscriptionDescription'));
        console.log('Subscription Category:', formData.get('subscriptionCategory'));

        const selectedProducts = Array.from(document.querySelectorAll('#selected-products .list-group-item'))
            .map(item => ({
                name: item.innerText.split(' - ')[0],
                price: item.innerText.split(' - ')[1].split(' ')[0]
            }));

        console.log('Selected Products:', selectedProducts);

        // Close the modal after form submission
        const addSubscriptionModal = bootstrap.Modal.getInstance(document.getElementById('addSubscriptionModal'));
        addSubscriptionModal.hide();

        // Optionally reset the form after submission
        this.reset();
        document.getElementById('selected-products').innerHTML = ''; // Clear selected products
    });
});



