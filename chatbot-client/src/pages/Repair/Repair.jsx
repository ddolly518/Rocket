import { Link, useNavigate } from "react-router-dom";
import api from "../../api/axiosInstance";
import { useState } from "react";

import { Group } from "../../components/Group/Group";
import { GroupWrapper } from "../../components/GroupWrapper/GroupWrapper";

import Logo from "../../assets/logo.png";
import Saly from "../../assets/saly.png";

import "./Repair.css";

export const Repair = () => {
  const [message, setMessage] = useState("");
  const [conversationId, setConversationId] = useState("");
  const [userText, setUserText] = useState("");
  const [aiText, setAiText] = useState("");

  const navigate = useNavigate();

  const handleChat = async () => {
    if (!message.trim()) return;

    try {
      const response = await api.post("/chat/repairs", {
        conversationId: conversationId.trim()
          ? Number(conversationId) // 문자열 → 숫자 변환
          : null,
        message,
      });

      console.log("채팅 메시지 전송 성공:", response.data);

      setUserText(message);
      const aiResponse = response.data.data.content; 
      setAiText(aiResponse);
      setMessage("");
    } catch (err) {
      console.error(
        "채팅 메시지 전송 실패:",
        err.response?.data || err.message,
      );
    }
  };

  return (
    <div className="repair" data-model-id="858:453">
      <img className="image" alt="Image" src={Logo} onClick={() => navigate("/main")} style={{cursor: "pointer"}} />

      <div className="group-3">
        <div className="group-4">
          <div className="group-5">
            <div className="text-wrapper-2">{userText}</div>
            <div className="text-wrapper-3">{aiText}</div>
          </div>
        </div>

        <img className="saly" alt="Saly" src={Saly} />
      </div>

      <div className="group-7">
        <div className="text-wrapper-5">Send repair message</div>

        <div className="group-8">
          <div className="group-9">
            <div className="group-10">
              <div className="rectangle-3" />
              <input
                type="conversationId"
                placeholder="ConversationId"
                value={conversationId}
                onChange={(e) => setConversationId(e.target.value)}
                className="text-wrapper-6"
              />
            </div>
          </div>
        </div>

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

export default Repair;