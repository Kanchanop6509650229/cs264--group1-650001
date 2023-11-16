document.addEventListener("DOMContentLoaded", function () {
    // Check if the user is authenticated
    const isAuthenticated = sessionStorage.getItem("authenticated");
    if (isAuthenticated !== "true") {
      // If not authenticated, redirect to the login page
      window.location.href = "../../../index.html";
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
        const form_id = urlParams.get("formId");
        const form_order = urlParams.get("order");
        const student_name = urlParams.get("studentName");

        // Check the referring page
        const referringPage = document.referrer.toLowerCase();
        const allowedReferrer = "status.html";

        // If the referring page is not status.html, redirect to status.html
        if (referringPage.indexOf(allowedReferrer) === -1) {
           window.location.href = `../status.html?studentId=${user_id}`;
           return;
        }

        const QuitFormLink = document.getElementById('QuitLink');

        // Add a click event listener to the link
        QuitFormLink.addEventListener('click', function (event) {
            // Prevent the default behavior of the link (i.e., prevent it from navigating)
            event.preventDefault();

            // Construct the URL for the next page with the studentId parameter
            const nextPageUrl = `../../QuitForm/QuitForm.html?studentId=${user_id}`;

            // Redirect to the next page
            window.location.href = nextPageUrl;
        });

        const LateFormLink = document.getElementById('LateFormLink');

                // Add a click event listener to the link
                LateFormLink.addEventListener('click', function (event) {
                    // Prevent the default behavior of the link (i.e., prevent it from navigating)
                    event.preventDefault();

                    // Construct the URL for the next page with the studentId parameter
                    const nextPageUrl = `../../LateForm/lateform.html?studentId=${user_id}`;

                    // Redirect to the next page
                    window.location.href = nextPageUrl;
                });

      // Get the "ขอเพิ่ม/ถอนรายวิชา (drop w)" link element
      const addDropLink = document.getElementById('addDropLink');

      // Add a click event listener to the link
      addDropLink.addEventListener('click', function (event) {
          // Prevent the default behavior of the link (i.e., prevent it from navigating)
          event.preventDefault();

          // Construct the URL for the next page with the studentId parameter
          const nextPageUrl = `../../Add_Drop_item/add_drop.html?studentId=${user_id}`;

          // Redirect to the next page
          window.location.href = nextPageUrl;
      });

        const OtherFormLink = document.getElementById('otherFormLink');

                    // Add a click event listener to the link
                    OtherFormLink.addEventListener('click', function (event) {
                        // Prevent the default behavior of the link (i.e., prevent it from navigating)
                        event.preventDefault();

                        // Construct the URL for the next page with the studentId parameter
                        const nextPageUrl = `../../OtherForm/otherform.html?studentId=${user_id}`;

                        // Redirect to the next page
                        window.location.href = nextPageUrl;
                    });

        const statusLink = document.getElementById('statusLink');

        // Add a click event listener to the link
        statusLink.addEventListener('click', function (event) {
        // Prevent the default behavior of the link (i.e., prevent it from navigating)
            event.preventDefault();

            // Construct the URL for the next page with the studentId parameter
            const nextPageUrl = `../status.html?studentId=${user_id}`;

            // Redirect to the next page
            window.location.href = nextPageUrl;
        });

        // Fetch student data by studentId
                $.ajax({
                  type: "GET",
                  url: `/api/form/getQuitFormByFormId?formId=${form_id}`,
                  contentType: "application/json",
                  success: function (quitForm) {
                      if (quitForm) {

                          // Populate the input fields with fetched data
                          document.getElementById("semester").value = quitForm.semester;
                          document.getElementById("year").value = quitForm.year;
                          document.getElementById("quitReason").value = quitForm.quitReason;
                          if(quitForm.quitReason === "tu") {
                            document.getElementById("tuFaculty").value = quitForm.tuFaculty;
                            document.getElementById("tuDepartment").value = quitForm.tuDepartment;
                          } else {
                            document.getElementById("university").value = quitForm.university;
                            document.getElementById("faculty").value = quitForm.faculty;
                            document.getElementById("department").value = quitForm.department;
                          }
                          document.getElementById("outstandingDebt").value = quitForm.outstandingDebt;
                            document.getElementById("reqGrade").value = quitForm.reqGrade;

                            toggleFields();
                            disableAllInputs();
                      } else {
                          console.log("No data received for formId:", form_id);
                          // Log the error to the console for debugging
                      }
                  },
                  error: function (error) {
                      console.log("Error fetching student data:", error);
                  },
              });


          // Fetch student data by studentId
              $.ajax({
                type: "GET",
                url: `/api/form/getTrackingQuitForm?formId=${form_id}`,
                contentType: "application/json",
                success: function (TrackingQuit) {
                console.log("Form data received:", TrackingQuit);
                // Get the table body element
                var tableBody = document.getElementById("requestTable").getElementsByTagName("tbody")[0];

                var row = requestTable.insertRow(-1);

                // Add cells to the row and populate them with data
                var orderCell = row.insertCell(0);
                var dateTimeCell = row.insertCell(1);
                var statusCell = row.insertCell(2);
                var formNameCell = row.insertCell(3);
                var studentNameCell = row.insertCell(4);
                var detailsCell = row.insertCell(5);

                orderCell.innerHTML = form_order;
                dateTimeCell.innerHTML = TrackingQuit.dateTime; // Replace with the actual property name
                if (TrackingQuit.status === 'อนุมัติ') {
                    statusCell.innerHTML = '<div style="display: inline-block; border-radius: 17px; border: 1px solid #0C7348; background: rgba(53, 216, 148, 0.70); padding: 15px 40px;">' + TrackingQuit.status + '</div>';
                } else if (TrackingQuit.status === 'ปฏิเสธ') {
                    statusCell.innerHTML = '<div style="display: inline-block; border-radius: 17px; border: 1px solid #A41B3E; background: rgba(228, 128, 153, 0.70); padding: 15px 40px;">' + TrackingQuit.status + '</div>';
                } else {
                    statusCell.innerHTML = '<div style="display: inline-block; border-radius: 17px; border: 1px solid #E9BE2B; background: rgba(253, 225, 129, 0.70); padding: 15px 40px;">' + TrackingQuit.status + '</div>';
                }

                formNameCell.innerHTML = TrackingQuit.formName;
                studentNameCell.innerHTML = student_name;
                detailsCell.innerHTML = '<a href="#top" disable><ion-icon name="folder-open-outline"></ion-icon></a>';

                // Set border-right style for orderCell
                orderCell.style.borderRight = 'none';
                dateTimeCell.style.borderLeft = 'none';
                dateTimeCell.style.borderRight = 'none';
                statusCell.style.borderLeft = 'none';
                statusCell.style.borderRight = 'none';
                formNameCell.style.borderLeft = 'none';
                formNameCell.style.borderRight = 'none';
                studentNameCell.style.borderLeft = 'none';
                studentNameCell.style.borderRight = 'none';
                detailsCell.style.borderLeft = 'none';

                orderCell.style.fontWeight = 'bold';

                // Set background color based on formData.status
                if (TrackingQuit.status === 'อนุมัติ') {
                    row.style.backgroundColor = '#5CE5AC';
                } else if (TrackingQuit.status === 'ปฏิเสธ') {
                    row.style.backgroundColor = '#FB9FB6';
                } else {
                    row.style.backgroundColor = '#F9E6A6';
                }

                // Set the width for the statusCell
                statusCell.style.width = '420px'; // Adjust the width as needed
                orderCell.style.width = '150px';
                detailsCell.style.width = '150px';
                studentNameCell.style.width = '450px';
                dateTimeCell.style.width = '320px';
                },
                error: function (error) {
                    console.log("Error fetching student data:", error);
                },
            });

            window.addEventListener('beforeunload', function (e) {
              const enabledInputs = document.querySelectorAll('input:enabled');
              if (enabledInputs.length > 0) {
                  const confirmationMessage = "ข้อมูลอาจยังไม่ถูกบันทุุึก ยืนยันจะออกจากหน้าเว็ปไซต์หรือไม่";
                  e.returnValue = confirmationMessage; // Standard for most browsers
                  return confirmationMessage; // For some older browsers
              }
          });
          // Add an event listener for the 'unload' event to set sessionStorage if the user chooses to stay on the page
              window.addEventListener('unload', function () {
                  const enabledInputs = document.querySelectorAll('input:enabled');
                  if (enabledInputs.length > 0) {
                      // If the user stays on the page, set sessionStorage to indicate the user is still authenticated
                      sessionStorage.setItem("authenticated", "true");
                  }
              });
  }
  });


function logout() {
  // Update the sessionStorage variable to indicate the user is not authenticated
  sessionStorage.setItem("authenticated", "false");

  // Redirect to the login page or any other desired page
  window.location.href = "../../../index.html"; // Change the URL as needed
}

function previous() {
    const urlParams = new URLSearchParams(window.location.search);
    const user_id = urlParams.get("studentId");
  // Redirect to the login page or any other desired page
  window.location.href = `../status.html?studentId=${user_id}`; // Change the URL as needed
}

  function toggleEditing() {
       const urlParams = new URLSearchParams(window.location.search);
       const form_status = urlParams.get("status");

            if(form_status === 'รออาจารย์ที่ปรึกษาอนุมัติ'){
                // Get references to the input fields by their IDs
                      const reasonInput = document.getElementById("quitReason");
                      const semesterInput = document.getElementById("semester");
                      const yearInput = document.getElementById("year");
                      const tuFacultyInput = document.getElementById("tuFaculty");
                      const tuDepartmentInput = document.getElementById("tuDepartment");
                      const universityInput = document.getElementById("university");
                      const facultyInput = document.getElementById("faculty");
                      const departmentInput = document.getElementById("department");
                      const outstandingDebtInput = document.getElementById("outstandingDebt");
                      const reqGradeDebtInput = document.getElementById("reqGrade");

                      // Function to toggle the disabled attribute
                      function toggleDisabled(inputElement) {
                          inputElement.disabled = !inputElement.disabled;
                      }

                      // Check if the inputs are currently disabled, only enable them if they are
                      if (!semesterInput.disabled) {
                          return;
                      }

                      // Toggle the disabled attribute for each input field
                      toggleDisabled(reasonInput);
                      toggleDisabled(semesterInput);
                      toggleDisabled(yearInput);
                      toggleDisabled(tuFacultyInput);
                      toggleDisabled(tuDepartmentInput);
                      toggleDisabled(universityInput);
                      toggleDisabled(facultyInput);
                      toggleDisabled(departmentInput);
                      toggleDisabled(outstandingDebtInput);
                      toggleDisabled(reqGradeDebtInput);
            } else {
                Swal.fire({
                    title: "Failed",
                    text: "ไม่สามารถแก้ไขฟอร์มได้หากถูกอนุมัติจากอาจารย์ที่ปรึกษาแล้ว",
                    icon: "warning",
                    showConfirmButton: false,
                    timer: 2000, // Auto-close the alert after 2 seconds
                });
            }
  }

  function disableAllInputs() {
      const enabledInputs = document.querySelectorAll('input:enabled');
      enabledInputs.forEach((input) => {
          input.disabled = true;
      });
      document.getElementById("reqGrade").disabled = true;
      document.getElementById("semester").disabled = true;
      document.getElementById("quitReason").disabled = true;
      document.getElementById("tuFaculty").disabled = true;
      document.getElementById("tuDepartment").disabled = true;
      document.getElementById("university").disabled = true;
      document.getElementById("faculty").disabled = true;
      document.getElementById("department").disabled = true;
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

  // Save input values to localStorage when the form is submitted
  function submitForm(event) {
     const urlParams = new URLSearchParams(window.location.search);
     const form_status = urlParams.get("status");

     if(form_status === 'รออาจารย์ที่ปรึกษาอนุมัติ'){
      event.preventDefault(); // Prevent the default form submission

      // Create a JSON object to hold the form data
      const jsonData = {};

      // Add values from enabled input fields
      const formData = $("#quitForm").serializeArray();

      // Get the studentId from the URL parameters
      const urlParams = new URLSearchParams(window.location.search);
      const formId = urlParams.get("formId");

      // Convert formData to a JSON object
          formData.forEach(function (entry) {
              jsonData[entry.name] = entry.value;
          });

          // Manually get the selected value from the semester dropdown
          const semesterDropdown = $("#semester");
          jsonData["semester"] = semesterDropdown.val();
          const quitReasonDropdown = $("#quitReason");
          jsonData["quitReason"] = quitReasonDropdown.val();
          const reqGradeDropdown = $("#reqGrade");
          jsonData["reqGrade"] = reqGradeDropdown.val();

      // Send an AJAX request to your controller with the correct data format
      $.ajax({
        type: "POST",
        url: `/api/form/updateQuitForm?formId=${formId}`, // Replace with your actual endpoint
        data: JSON.stringify(jsonData), // Convert to JSON format
        contentType: "application/json", // Set content type to JSON
        success: function (response) {
        disableAllInputs();
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
     } else {
     event.preventDefault();
                Swal.fire({
                    title: "Failed",
                    text: "ไม่สามารถแก้ไขฟอร์มได้หากถูกอนุมัติจากอาจารย์ที่ปรึกษาแล้ว",
                    icon: "warning",
                    showConfirmButton: false,
                    timer: 2000, // Auto-close the alert after 2 seconds
                });
     }
    }