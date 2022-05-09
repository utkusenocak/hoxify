import React, { useState } from "react";
import Input from "../components/Input";
import { useTranslation } from "react-i18next";
import ButtonWithProgress from "../components/ButtonWithProgress";
import { withApiProgress } from "../shared/ApiProgress";
import { useDispatch } from "react-redux";
import { signUpHandler } from "../redux/authSlice";

const UserSignupPage = (props) => {
  const { t } = useTranslation();
  const dispatch = useDispatch();
  const [form, setForm] = useState({
    username: null,
    displayName: null,
    password: null,
    passwordRepeat: null,
  });
  const [errors, setErrors] = useState({});

  const onChange = (event) => {
    
    const { name, value } = event.target;
    const errorsCopy = { ...errors };

    errorsCopy[name] = undefined;
    if (name === "password" || name === "passwordRepeat") {
      if (name === "password" && value !== form.passwordRepeat) {
        errorsCopy.passwordRepeat = t("Password mismatch");
      } else if (name === "passwordRepeat" && value !== form.password) {
        errorsCopy.passwordRepeat = t("Password mismatch");
      } else {
        errorsCopy.passwordRepeat = undefined;
      }
    }
    setErrors(errorsCopy);
    const formCopy = { ...form };
    formCopy[name] = value;
    setForm(formCopy);
  };

  const onClickSignup = async (event) => {
    event.preventDefault();
    const { history } = props;
    const { push } = history;
    const { username, displayName, password } = form;
    const body = {
      username,
      displayName,
      password,
    };
    try {
      await dispatch(signUpHandler(body));
      push("/");
    } catch (error) {
      if (error.response.data.validationErrors) {
        setErrors(error.response.data.validationErrors);
      }
    }
  };

  const {
    username: usernameError,
    displayName: displayNameError,
    password: passwordError,
    passwordRepeat: passwordRepeatError,
  } = errors;
  const { pendingApiCall } = props;
  return (
    <div className="container">
      <form>
        <h1 className="text-center">{t("Sign Up")}</h1>
        <Input
          name="username"
          label={t("Username")}
          error={usernameError}
          onChange={onChange}
        />
        <Input
          name="displayName"
          label={t("Display Name")}
          error={displayNameError}
          onChange={onChange}
        />
        <Input
          name="password"
          label={t("Password")}
          error={passwordError}
          type="password"
          onChange={onChange}
        />
        <Input
          name="passwordRepeat"
          label={t("Password Repeat")}
          error={passwordRepeatError}
          type="password"
          onChange={onChange}
        />
        <div className="text-center">
          <ButtonWithProgress
            onClick={onClickSignup}
            disabled={pendingApiCall || passwordRepeatError !== undefined}
            pendingApiCall={pendingApiCall}
            text={t("Sign Up")}
          />
        </div>
      </form>
    </div>
  );
};
const UserSignupPageWithApiProgressForSignupRequest = withApiProgress(
  UserSignupPage,
  "/api/1.0/users"
);
const UserSignupPageWithApiProgressForAuthRequest = withApiProgress(
  UserSignupPageWithApiProgressForSignupRequest,
  "/api/1.0/auth"
);

export default UserSignupPageWithApiProgressForAuthRequest;
