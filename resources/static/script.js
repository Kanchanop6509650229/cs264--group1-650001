// Disable the other form when one of them is submitted
function disableOtherForm(formId) {
  if (formId === 'form1') {
    document.getElementById('form2').submitButton.disabled = true;
  } else if(formId === 'form2') {
    document.getElementById('form1').submitButton.disabled = true;
  } 
}

document.addEventListener("DOMContentLoaded", function () {
  const form1 = document.getElementById("form1");
  const submitBtn = document.getElementById("submitBtn");

  form1.addEventListener("submit", function (event) {
      event.preventDefault(); // Prevent the default form submission

      const username = document.getElementById("username").value;
      const password = document.getElementById("password").value;

      // Check if it's a custom login for an employee
    if (username === "employee" && password === "employee") {
      Swal.fire({
        icon: "success",
        title: "Login successful",
        text: "You will be redirected to the next page.",
    }).then(() => {
        window.location.href = "employeeHomepage.html"; // Redirect to web2.html
    });
    } else {
      // Your API endpoint
      const apiEndpoint = "https://restapi.tu.ac.th/api/v1/auth/Ad/verify";

      const myHeaders = new Headers();
      myHeaders.append("Application-Key", "TU29247a24c35881799b89dddfbefda01f3c1bae54f881d3629c0cdbc6f42cfcde0fc4a40f43377a73e8bb21966436063e");
      myHeaders.append("Content-Type", "application/json");

      const data = {
          UserName: username,
          PassWord: password
      };

      const requestOptions = {
          method: "POST",
          headers: myHeaders,
          body: JSON.stringify(data),
          redirect: "follow"
      };

      fetch(apiEndpoint, requestOptions)
          .then(response => response.json()) // Assuming the response is JSON
          .then(result => {
              if (result.status) {
                  Swal.fire({
                      icon: "success",
                      title: "Login successful",
                      text: "You will be redirected to the next page.",
                  }).then(() => {
                      // Determine where to redirect based on the "type" field
                      if (result.type === "student") {
                        // After a successful login and receiving the API response
                        // Extract the displayname_th from the result
                      const displayname_th = result.displayname_th;
                      const user_type = result.type;
                      
                      window.location.href = `studentWeb/studentHomepage.html?displayname_th=${displayname_th}&type=${user_type}`;
                      } else if (result.type === "employee") {
                      window.location.href = "employeeHomepage.html"; // Redirect to the employee page
                      }
                  });
              } else {
                  Swal.fire({
                      icon: "error",
                      title: result.message,
                      text: result.Description,
                  });
              }
          })
          .catch(error => console.log("error", error));
    }
      
  });
});
