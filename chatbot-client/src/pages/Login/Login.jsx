import { Link } from "react-router-dom";
import api from "../../api/axiosInstance";
import { useState } from "react";

import {Apple} from "../../components/Apple/Apple";
import {Facebook} from "../../components/Facebook/Facebook";
import {Google} from "../../components/Google/Google";
import {Group} from "../../components/Group/Group";
import {GroupWrapper} from "../../components/GroupWrapper/GroupWrapper";

import Logo from "../../assets/logo.png";
import Saly from "../../assets/saly.png";
import Invisible from "../../assets/Invisible.png";

import "./Login.css";

export const Login = () => {
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");

    const handleLogin = async () => {
        try {
            const response = await api.post("/auth/login", {
                email,
                password
            });

            console.log("로그인 성공:", response.data);
            window.location.href = "/main";
        } catch (err) {
            console.error("로그인 실패:", err.response?.data || err.message);
        }
    };

    return (
        <div className="login" data-model-id="858:453">
            <img
                className="image"
                alt="Image"
                src={Logo}
            />

            <div className="group-3">
                <div className="group-4">
                    <div className="group-5">
                        <div className="text-wrapper-2">Sign in to</div>
                        <div className="text-wrapper-3">FixTrack</div>
                    </div>

                    <div className="group-6">
                        <p className="p">If you don't have an account register</p>
                        <p className="you-can-register">
                            <span className="span">You can&nbsp;&nbsp; </span>
                            <Link to="/signup" className="text-wrapper-4">Register here !</Link>
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
                <div className="text-wrapper-5">Sign in</div>
            
                <Group
                    className="group-15"
                    divClassName="group-15-instance"
                    rectangleClassName="group-instance"
                    text="Enter email"
                    value={email}
                    onChange={(e) => setEmail(e.target.value)}
                    type="email"
                />

                <div className="group-8">
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

                    <div className="text-wrapper-7">Forgot password ?</div>
                </div>

                <GroupWrapper
                    className="group-14"
                    rectangleClassName="group-14-instance"
                    text="Login"
                    onClick={handleLogin}
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

export default Login;