import React, { Component } from "react";
import Form from "react-validation/build/form";
import "./preference.scss";
import AuthService from "../../services/auth.service";
import axios from 'axios';

export default class Login extends Component {
  constructor(props) {
    super(props);
    // initial state of preferences
    this.state = { countries: [], categories: [], checked: false };
    this.handleSave = this.handleSave.bind(this);
    this.handleCheck = this.handleCheck.bind(this);
    this.handleInputChange = this.handleInputChange.bind(this);
    this.handleCntrCheck = this.handleCntrCheck.bind(this);
    this.handleCntrInputChange = this.handleCntrInputChange.bind(this);
    this.deleteMyAcc = this.deleteMyAcc.bind(this);
  }
  // gets user preferences from server
  componentDidMount() {
    fetch("http://localhost:8080/HuskyTimes/GetPreferences.htm?username=" + JSON.parse(localStorage.getItem("user")).username).then((results) => { return results.json() }).then((results) => this.setState({ categories: results.categories, countries: results.countries }))
  }

  // delete user account
  deleteMyAcc(event) { // confirmation of request
    if (window.confirm("Are you sure want to delete your account?")) {
      axios.post("http://localhost:8080/HuskyTimes/DeleteUser.htm?username=" + JSON.parse(localStorage.getItem("user")).username).then((data) => localStorage.removeItem("user"))
      AuthService.logout(); // logs user out and deletes account
      window.location.replace("/");
    }
  }

  // handles input change of categories checked
  handleInputChange(event) {
    const target = event.target;
    if (!target.checked) {
      const cats = this.state.categories;
      var index = cats.indexOf(target.name)
      if (index !== -1) {
        cats.splice(index, 1);
        this.setState({ categories: cats });
      }

    } else {
      const cats = this.state.categories;
      cats.push(target.name)
      this.setState({ categories: cats })
    }
  }

  // handles input check of countries checked 
  handleCntrInputChange(event) {
    const target = event.target;
    if (!target.checked) {
      const cats = this.state.countries;
      var index = cats.indexOf(target.name)
      if (index !== -1) {
        cats.splice(index, 1);
        this.setState({ countries: cats });
      }

    } else {
      const cats = this.state.countries;
      cats.push(target.name)
      this.setState({ countries: cats })
    }
  }

  // saves new user categories checked
  handleSave(e) {
    e.preventDefault();
    var catlist = [];
    var countryList = [];
    var catagory = document.getElementsByClassName("catagory");
    for (var i = 0; i < catagory.length; i++) {
      if (catagory.item(i).checked) {
        catlist.push(catagory.item(i).getAttribute("catname"));
      }
    }
    // saves new user countries checked
    localStorage.setItem('userCat', catlist);
    var country = document.getElementsByClassName("country");
    for (var j = 0; j < country.length; j++) {
      if (country.item(j).checked) {
        countryList.push(country.item(j).getAttribute("cntrname"));
      }
    }
    // updates user preferences
    axios.post("http://localhost:8080/HuskyTimes/UpdatePreferences.htm", { "username": JSON.parse(localStorage.getItem("user")).username, "categories": catlist.toString(), "countries": countryList.toString() })
    localStorage.setItem('userCntry', countryList);
    alert("Changes saved successfully!")
  }

  handleCheck(val) {
    return this.state.categories.indexOf(val) > -1;
  }
  handleCntrCheck(val) {
    return this.state.countries.indexOf(val) > -1;
  }

  render() {
    return (
      <div className="loginPage">
        <div className="card card-container">
          <Form
            onSubmit={this.handleSave}
            ref={c => {
              this.form = c;
            }} className="loginForm1"
          >
            <div className="form-group prefMainDiv">
              <div className="prefHead"><h3>Catagory Preference List</h3></div>

              <div className="checkSection">

                <div className="checkboxClass"><input type="checkbox" name="Business" catname="Business" className="catagory" onChange={this.handleInputChange} checked={this.handleCheck("Business")} /> <label>Business</label></div>
                <div className="checkboxClass"><input type="checkbox" name="Entertainment" catname="Entertainment" className="catagory" onChange={this.handleInputChange} checked={this.handleCheck("Entertainment")} /> <label>Entertainment</label></div>
                <div className="checkboxClass"><input type="checkbox" name="General" catname="General" className="catagory" onChange={this.handleInputChange} checked={this.handleCheck("General")} /> <label>General</label></div>
                <div className="checkboxClass"><input type="checkbox" name="Health" catname="Health" className="catagory" onChange={this.handleInputChange} checked={this.handleCheck("Health")} /> <label>Health</label></div>

                <div className="checkboxClass"><input type="checkbox" name="Science" catname="Science" className="catagory" onChange={this.handleInputChange} checked={this.handleCheck("Science")} /> <label>Science</label></div>
                <div className="checkboxClass"><input type="checkbox" name="Sports" catname="Sports" className="catagory" onChange={this.handleInputChange} checked={this.handleCheck("Sports")} /> <label>Sports</label></div>
                <div className="checkboxClass"><input type="checkbox" name="Technology" catname="Technology" className="catagory" onChange={this.handleInputChange} checked={this.handleCheck("Technology")} /> <label>Technology</label></div>
              </div>

            </div>
            <div className="form-group prefMainDiv">
              <div className="prefHead"><h3>Country Preference List</h3></div>
              <div>
                <div className="checkSection">
                  <div className="checkboxClass"><input type="checkbox" onChange={this.handleCntrInputChange} checked={this.handleCntrCheck("United States of America")} name="United States of America" cntrname="United States of America" className="country" /> <label>United States of America</label></div>
                  <div className="checkboxClass"><input type="checkbox" onChange={this.handleCntrInputChange} checked={this.handleCntrCheck("United Kingdom")} name="United Kingdom" cntrname="United Kingdom" className="country" /> <label>United Kingdom</label></div>
                  <div className="checkboxClass"><input type="checkbox" onChange={this.handleCntrInputChange} checked={this.handleCntrCheck("Canada")} name="Canada" cntrname="Canada" className="country" /> <label>Canada</label></div>
                  <div className="checkboxClass"> <input type="checkbox" onChange={this.handleCntrInputChange} checked={this.handleCntrCheck("China")} name="China" cntrname="China" className="country" /> <label>China</label></div>
                  <div className="checkboxClass"> <input type="checkbox" onChange={this.handleCntrInputChange} checked={this.handleCntrCheck("Russia")} name="Russia" cntrname="Russia" className="country" /> <label>Russia</label></div>
                  <div className="checkboxClass"> <input type="checkbox" onChange={this.handleCntrInputChange} checked={this.handleCntrCheck("France")} name="France" cntrname="France" className="country" /> <label>France</label></div>
                </div>
                <div className="checkSection">
                  <div className="checkboxClass"> <input type="checkbox" onChange={this.handleCntrInputChange} checked={this.handleCntrCheck("Philippines")} name="Philippines" cntrname="Philippines" className="country" /> <label>Philippines</label></div>
                  <div className="checkboxClass"> <input type="checkbox" onChange={this.handleCntrInputChange} checked={this.handleCntrCheck("United Arab Emirates")} name="United Arab Emirates" cntrname="United Arab Emirates" className="country" /> <label>United Arab Emirates</label></div>
                  <div className="checkboxClass"><input type="checkbox" onChange={this.handleCntrInputChange} checked={this.handleCntrCheck("Australia")} name="Australia" cntrname="Australia" className="country" /> <label>Australia</label> </div>
                  <div className="checkboxClass"><input type="checkbox" onChange={this.handleCntrInputChange} checked={this.handleCntrCheck("Argentina")} name="Argentina" cntrname="Argentina" className="country" /> <label>Argentina</label></div>
                  <div className="checkboxClass"> <input type="checkbox" onChange={this.handleCntrInputChange} checked={this.handleCntrCheck("South Korea")} name="South Korea" cntrname="South Korea" className="country" /> <label>South Korea</label></div>
                  <div className="checkboxClass"><input type="checkbox" onChange={this.handleCntrInputChange} checked={this.handleCntrCheck("Indonesia")} name="Indonesia" cntrname="Indonesia" className="country" /> <label>Indonesia</label></div>
                </div>
              </div>
              <div className="form-group newsButtons">
                <button
                  className="btn btn-primary btn-block"
                >
                  <span>Save</span>
                </button>
              </div>
            </div>

          </Form>
          <div className="form-group newsButtons"><button onClick={this.deleteMyAcc}
            className="btn btn-primary btn-block"
          ><span>Delete My Account</span>
          </button></div>
        </div>
      </div>
    );
  }
}