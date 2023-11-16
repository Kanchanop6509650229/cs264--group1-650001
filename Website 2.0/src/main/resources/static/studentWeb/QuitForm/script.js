document.addEventListener("DOMContentLoaded", function () {
    // Check if the user is authenticated
    const isAuthenticated = sessionStorage.getItem("authenticated");
    if (isAuthenticated !== "true") {
      // If not authenticated, redirect to the login page
      window.location.href = "../../index.html";
      return;
    } else {
          // Add event listener for screen resize
  window.addEventListener("resize", function () {
    checkScreenSize();
  });

  // Function to check and display SweetAlert for screen size
  function checkScreenSize() {
    const screenWidth = window.innerWidth || document.documentElement.clientWidth || document.body.clientWidth;

    // Check if the screen size is less than 1800px
    if (screenWidth < 1490) {
      Swal.fire({
        icon: "warning",
        title: "เราขอแนะนำให้ใช้เบราว์เซอร์ในขนาดเต็มจอ หรือความกว้างมากกว่า 1490px",
        showCancelButton: false,
        confirmButtonText: "OK",
      }).then(() => {
        // Check screen size again after the user clicks OK
        checkScreenSize();
      });
    }
  }

  // Initial check when the page loads
  checkScreenSize();

      const urlParams = new URLSearchParams(window.location.search);
      const user_id = urlParams.get("studentId");
      // Get the "ขอเพิ่ม/ถอนรายวิชา (drop w)" link element
      const addDropLink = document.getElementById('addDropLink');

      // Add a click event listener to the link
      addDropLink.addEventListener('click', function (event) {
          // Prevent the default behavior of the link (i.e., prevent it from navigating)
          event.preventDefault();

          // Construct the URL for the next page with the studentId parameter
          const nextPageUrl = `../Add_Drop_item/add_drop.html?studentId=${user_id}`;

          // Redirect to the next page
          window.location.href = nextPageUrl;
      });

      const lateFormLink = document.getElementById('lateFormLink');

      // Add a click event listener to the link
      lateFormLink.addEventListener('click', function (event) {
          // Prevent the default behavior of the link (i.e., prevent it from navigating)
          event.preventDefault();

          // Construct the URL for the next page with the studentId parameter
          const nextPageUrl = `../LateForm/lateform.html?studentId=${user_id}`;

          // Redirect to the next page
          window.location.href = nextPageUrl;
      });

        const statusLink = document.getElementById('statusLink');

        // Add a click event listener to the link
        statusLink.addEventListener('click', function (event) {
        // Prevent the default behavior of the link (i.e., prevent it from navigating)
            event.preventDefault();

            // Construct the URL for the next page with the studentId parameter
            const nextPageUrl = `../Status/status.html?studentId=${user_id}`;

            // Redirect to the next page
            window.location.href = nextPageUrl;
        });
      
          const OtherFormLink = document.getElementById('otherFormLink');

            // Add a click event listener to the link
            OtherFormLink.addEventListener('click', function (event) {
                // Prevent the default behavior of the link (i.e., prevent it from navigating)
                event.preventDefault();

                // Construct the URL for the next page with the studentId parameter
                const nextPageUrl = `../OtherForm/otherform.html?studentId=${user_id}`;

                // Redirect to the next page
                window.location.href = nextPageUrl;
            });
  }
  });


function logout() {
  // Update the sessionStorage variable to indicate the user is not authenticated
  sessionStorage.setItem("authenticated", "false");

  // Redirect to the login page or any other desired page
  window.location.href = "../../index.html"; // Change the URL as needed
}

  function toggleFields() {
    // Get the selected option
    var quitReason = document.getElementsByName('quitReason')[0];
    
    // Get the input fields to be toggled
    var tuFacultyField = document.getElementById('tuFaculty');
    var tuDepartmentField = document.getElementById('tuDepartment');
    var universityField = document.getElementById('university');
    var facultyField = document.getElementById('faculty');
    var departmentField = document.getElementById('department');
    var tuFacultyLabelField = document.getElementById('tuFacultyLabel');
    var tuDepartmentLabelField = document.getElementById('tuDepartmentLabel');
    var universityLabelField = document.getElementById('universityLabel');
    var facultyLabelField = document.getElementById('facultyLabel');
    var departmentLabelField = document.getElementById('departmentLabel');

    // Reset all fields to hidden
    tuFacultyField.style.display = 'none';
    tuDepartmentField.style.display = 'none';
    universityField.style.display = 'none';
    facultyField.style.display = 'none';
    departmentField.style.display = 'none';
    tuFacultyLabelField.style.display = 'none';
    tuDepartmentLabelField.style.display = 'none';
    universityLabelField.style.display = 'none';
    facultyLabelField.style.display = 'none';
    departmentLabelField.style.display = 'none';

    tuFacultyField.disabled = true;
    tuDepartmentField.disabled = true;
    universityField.disabled = true;
    facultyField.disabled = true;
    departmentField.disabled = true;

    tuFacultyField.removeAttribute('required');
    tuDepartmentField.removeAttribute('required');
    universityField.removeAttribute('required');
    facultyField.removeAttribute('required');
    departmentField.removeAttribute('required');


    // Show fields based on the selected option
    if (quitReason.value === 'tu') {
      tuFacultyField.style.display = 'block';
      tuDepartmentField.style.display = 'block';
      tuFacultyLabelField.style.display = 'block';
      tuDepartmentLabelField.style.display = 'block';

      tuFacultyField.disabled = false;
      tuDepartmentField.disabled = false;

      tuFacultyField.setAttribute('required', 'required');
      tuDepartmentField.setAttribute('required', 'required');
    } else if (quitReason.value === 'other') {
        universityField.style.display = 'block';
        facultyField.style.display = 'block';
        departmentField.style.display = 'block';
        universityLabelField.style.display = 'block';
        facultyLabelField.style.display = 'block';
        departmentLabelField.style.display = 'block';

        universityField.disabled = false;
        facultyField.disabled = false;
        departmentField.disabled = false;

        universityField.setAttribute('required', 'required');
        facultyField.setAttribute('required', 'required');
        departmentField.setAttribute('required', 'required');
    }
}

// Attach the toggleFields function to the change event of the quitReason select element
document.getElementsByName('quitReason')[0].addEventListener('change', toggleFields);

// Initially call the function to set the initial state based on the default value
toggleFields();

// Save input values to localStorage when the form is submitted
function submitForm(event) {
    event.preventDefault(); // Prevent the default form submission

    // Create a JSON object to hold the form data
    const jsonData = {};

    // Add values from enabled input fields
    const formData = $("#quitForm").serializeArray();

    // Get the studentId from the URL parameters
    const urlParams = new URLSearchParams(window.location.search);
    const studentId = urlParams.get("studentId");

    // Convert formData to a JSON object
        formData.forEach(function (entry) {
            jsonData[entry.name] = entry.value;
        });

        // Add the studentId to the jsonData object
        jsonData["studentId"] = studentId;
        jsonData["formStatus"] = "รออาจารย์ที่ปรึกษาอนุมัติ";

        // Manually get the selected value from the semester dropdown
            const semesterDropdown = $("#semester");
            jsonData["semester"] = semesterDropdown.val();

            const reqGradeDropdown = $("#reqGrade");
            jsonData["reqGrade"] = reqGradeDropdown.val();

            const quitReasonDropdown = $("#quitReason");
            jsonData["quitReason"] = quitReasonDropdown.val();

    // Send an AJAX request to your controller with the correct data format
    $.ajax({
      type: "POST",
      url: "/api/form/quitForm", // Replace with your actual endpoint
      data: JSON.stringify(jsonData), // Convert to JSON format
      contentType: "application/json", // Set content type to JSON
      success: function (response) {
        // Show a success message with SweetAlert2
        Swal.fire({
          title: "Success",
          text: response,
          icon: "success",
          showConfirmButton: false,
          timer: 2000, // Auto-close the alert after 2 seconds
        });
      },
      error: function (error) {
        // Show an error message with SweetAlert2
        Swal.fire({
          title: "Error",
          text: "Failed to save data. Please try again.",
          icon: "error",
        });
      },
    });
  }