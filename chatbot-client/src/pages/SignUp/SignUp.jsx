import { Link } from "react-router-dom";
import api from "../../api/axiosInstance";
import { useState } from "react";

import {Apple} from "../../components/Apple/Apple";
import {Facebook} from "../../components/Facebook/Facebook";
import {Google} from "../../components/Google/Google";
import {Group} from "../../components/Group/Group";
import {GroupWrapper} from "../../components/GroupWrapper/GroupWrapper";
import UserTypeToggle from "../../components/UserTypeToggle/UserTypeToggle";

import Logo from "../../assets/logo.png";
import Saly from "../../assets/saly.png";
import Invisible from "../../assets/Invisible.png";

import "./SignUp.css";

export const SignUp = () => {
    const [userType, setUserType] = useState("");
    const [email, setEmail] = useState("");
    const [nickname, setNickname] = useState("");
    const [password, setPassword] = useState("");
    const [confirmPassword, setConfirmPassword] = useState("");
    
    const handleSignUp = async () => {
        try {
            const response = await api.post("/auth/signup", {
                userType,
                email,
                password,
                confirmPassword
            });

            console.log("회원가입 성공:", response.data);
            window.location.href = "/";
        } catch (err) {
            console.error("회원가입 실패:", err.response?.data || err.message);
        }
    };
    
    return (
        <div className="sign-up" data-model-id="858:453">
            <img
                className="image"
                alt="Image"
                src={Logo}
            />

            <div className="group-3">
                <div className="group-4">
                    <div className="group-5">
                        <div className="text-wrapper-2">Sign Up to</div>
                        <div className="text-wrapper-3">FixTrack</div>
                    </div>

                    <div className="group-6">
                        <p className="p">If you already have an account</p>
                        <p className="you-can-register">
                            <span className="span">You can&nbsp;&nbsp; </span>
                        </p>
                    </div>
                </div>

                <img
                    className="saly"
                    alt="Saly"
                    src={Saly}
                />
            </div>

            <div className="group-7">
                <div className="text-wrapper-5">Sign Up</div>
            
                <UserTypeToggle
                    value={userType}
                    onChange={setUserType}
                />

                <Group
                    className="group-15"
                    divClassName="group-15-instance"
                    rectangleClassName="group-instance"
                    text="Enter email"
                    value={email}
                    onChange={(e) => setEmail(e.target.value)}
                    type="email"
                />

                <Group
                    className="group-16"
                    divClassName="group-16-instance"
                    rectangleClassName="group-instance"
                    text="Create User name"
                    value={nickname}
                    onChange={(e) => setNickname(e.target.value)}
                    type="nickname"
                />

                <div className="group-18">
                    <div className="group-9">
                        <div className="group-10">
                            <div className="rectangle-3"/>
                            <input
                                type="password"
                                placeholder="Password"
                                value={password}
                                onChange={(e) => setPassword(e.target.value)}
                                className="text-wrapper-6"/>
                        </div>

                        <img
                            className="invisible"
                            alt="Invisible"
                            src={Invisible}
                        />
                    </div>
                </div>

                <div className="group-8">
                    <div className="group-9">
                        <div className="group-10">
                            <div className="rectangle-3"/>
                            <input
                                type="password"
                                placeholder="Confirm Password"
                                value={confirmPassword}
                                onChange={(e) => setConfirmPassword(e.target.value)}
                                className="text-wrapper-6"/>
                        </div>

                        <img
                            className="invisible"
                            alt="Invisible"
                            src={Invisible}
                        />
                    </div>
                </div>

                <GroupWrapper
                    className="group-14"
                    rectangleClassName="group-14-instance"
                    text="Register"
                    onClick={handleSignUp}
                />
                <div className="text-wrapper-8">or continue with</div>
            
                <div className="group-11">
                    <div className="group-12">
                        <Facebook className="facebook-instance"/>
                        <Apple className="apple-instance" />
                        <Google className="google-instance" />
                    </div>
                </div>
            </div>
        </div>
    );
};

export default SignUp;