document.addEventListener("DOMContentLoaded", function () {
    // Check if the user is authenticated
    const isAuthenticated = sessionStorage.getItem("authenticated");
    if (isAuthenticated !== "true") {
      // If not authenticated, redirect to the login page
      window.location.href = "../../index.html";
      return;
    } else {
    const user_id = urlParams.get("studentId");
          
    }
  });


function logout() {
  // Update the sessionStorage variable to indicate the user is not authenticated
  sessionStorage.setItem("authenticated", "false");

  // Redirect to the login page or any other desired page
  window.location.href = "../../index.html"; // Change the URL as needed
}
    
// Function to add a new row
    function addRow() {
      const table = document.getElementById("requestTable");
      const firstRow = table.querySelector("tbody tr:first-child");
      if (firstRow) {
          const newRow = firstRow.cloneNode(true);
          const newInputs = newRow.querySelectorAll("input");
          newInputs.forEach((input) => (input.value = "")); // Clear input values in the new row

          // Set default value for select elements in the new row
          const newSelects = newRow.querySelectorAll("select");
          newSelects.forEach((select) => {
              select.value = select.querySelector("option").value;
          });

          table.querySelector("tbody").appendChild(newRow);
      }
  }

  // Function to remove the last row (keeping at least one row)
  function removeRow() {
      const table = document.getElementById("requestTable");
      const rows = table.querySelectorAll("tbody tr");
      if (rows.length > 1) {
          table.querySelector("tbody").removeChild(rows[rows.length - 1]);
      } else {
              Swal.fire({
                title: "Can not delete row",
                text: "You must have at least one row.",
                icon: "error",
                showConfirmButton: false,
                timer: 2000, // Auto-close the alert after 1 seconds
              });
      }
  }

  function submitForm(event) {
      event.preventDefault(); // Prevent the default form submission

      const form = document.getElementById("addDropForm");
      const formData = new FormData(form);

      // Get the studentId from the URL parameters
      const urlParams = new URLSearchParams(window.location.search);
      const userId = urlParams.get("studentId");

      // Get all rows in the table
      const tableRows = document.querySelectorAll("#requestTable tbody tr");

      // Create an array to store data for all rows
      const rowDataArray = [];

      // Iterate over each row
      tableRows.forEach((row) => {
          const rowData = {};

          // Get inputs and selects within the current row
          const inputsAndSelects = row.querySelectorAll("input, select");

          // Iterate over each input and select to collect data
          inputsAndSelects.forEach((inputOrSelect) => {
              const name = inputOrSelect.name;
              const value = inputOrSelect.value;

              // Skip the submit button
              if (name !== "submit") {
                  rowData[name] = value;
              }
          });

          // Add userId to rowData
          rowData["userId"] = userId;

          // Set formStatus to "Nothing"
          rowData["formStatus"] = "รออาจารย์ที่ปรึกษาอนุมัติ";

          // Add rowData to the array
          rowDataArray.push(rowData);
      });

      // Send the array of row data to the server using an AJAX request
      $.ajax({
          type: "POST",
          url: "/api/form/add-dropForm", // Replace with your actual API endpoint
          contentType: "application/json",
          data: JSON.stringify(rowDataArray),
          success: function (response) {
              // Handle success, e.g., show a success message
              console.log("Form submitted successfully", response);
              Swal.fire({
                  title: "Form submitted successfully",
                  icon: "success",
                  showConfirmButton: false,
                  timer: 2000,
              });
          },
          error: function (error) {
              // Handle error, e.g., show an error message
              console.error("Error submitting form", error);
              Swal.fire({
                  title: "Error submitting form",
                  text: "Please try again later.",
                  icon: "error",
                  showConfirmButton: false,
                  timer: 2000,
              });
          },
      });
  }
