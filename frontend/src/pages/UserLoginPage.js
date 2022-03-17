import React from "react";
import Input from "../components/Input";

class UserLoginPage extends React.Component {
  state = {
    username: null,
    password: null,
  };

  onChange = (event) => {
    const { name, value } = event.target;
    this.setState({
      [name]: value,
    });
  };
  render() {
    return (
      <div className="container">
        <form>
          <h1 className="text-center">Login</h1>
          <Input name="username" label="Username" onChange={this.onChange} />
          <Input
            name="password"
            label="Password"
            type="password"
            onChange={this.onChange}
          />
          <div className="text-center">
            <button className="btn btn-primary">Login</button>
          </div>
        </form>
      </div>
    );
  }
}

export default UserLoginPage;
