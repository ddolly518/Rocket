import { Link } from "react-router-dom";
import api from "../../api/axiosInstance";
import { useState } from "react";

import {Group} from "../../components/Group/Group";
import {GroupWrapper} from "../../components/GroupWrapper/GroupWrapper";

import Logo from "../../assets/logo.png";
import Saly from "../../assets/saly.png";

import "./Chat.css";

export const Chat = () => {
    const [message, setMessage] = useState("");

    const handleChat = async () => {
        try {
            const response = await api.post("/conversations", {
                //conversationId,
                message
            });

            console.log("채팅 메시지 전송 성공:", response.data);
        } catch (err) {
            console.error("채팅 메시지 전송 실패:", err.response?.data || err.message);
        }
    };

    return (
        <div className="chat" data-model-id="858:453">
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
                    text="Enter message"
                    value={message}
                    onChange={(e) => setMessage(e.target.value)}
                    type="message"
                />

                <GroupWrapper
                    className="group-14"
                    rectangleClassName="group-14-instance"
                    text="Send"
                    onClick={handleChat}
                />
            </div>
        </div>
    );
};

export default Chat;