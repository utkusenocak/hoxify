import React, { useEffect, useState } from "react";
import Input from "../components/Input";
import { withTranslation } from "react-i18next";
import ButtonWithProgress from "../components/ButtonWithProgress";
import { withApiProgress } from "../shared/ApiProgress";
import { connect } from "react-redux";
import { loginHandler } from "../redux/authSlice";

const UserLoginPage = (props) => {
  const [username, setUsername] = useState();
  const [password, setPassword] = useState();
  const [error, setError] = useState();

  useEffect(() => {
    setError(undefined);
  }, [username, password]);

  const onClickLogin = async (event) => {
    event.preventDefault();

    const creds = {
      username,
      password,
    };
    const { history, dispatch } = props;
    const { push } = history;
    setError(undefined);
    try {
      await dispatch(loginHandler(creds));
      push("/");
    } catch (apiError) {
      setError(apiError.response.data.message);
      // this.setState({
      //   error: apiError.response.data.message,
      // });
    }
  };

  const { t, pendingApiCall } = props;

  const buttonEnabled = username && password;

  return (
    <div className="container">
      <form>
        <h1 className="text-center">{t("Login")}</h1>
        <Input
          name="username"
          label={t("Username")}
          onChange={(event) => setUsername(event.target.value)}
        />
        <Input
          name="password"
          label={t("Password")}
          type="password"
          onChange={(event) => setPassword(event.target.value)}
        />
        {error && <div className="alert alert-danger">{error}</div>}
        <div className="text-center">
          <ButtonWithProgress
            onClick={onClickLogin}
            pendingApiCall={pendingApiCall}
            disabled={!buttonEnabled || pendingApiCall}
            text={t("Login")}
          />
        </div>
      </form>
    </div>
  );
};
const UserLoginPageWithApiProgress = withApiProgress(
  UserLoginPage,
  "/api/1.0/auth"
);
const UserLoginPageWithTranslation = withTranslation()(
  UserLoginPageWithApiProgress
);

export default connect()(UserLoginPageWithTranslation);
