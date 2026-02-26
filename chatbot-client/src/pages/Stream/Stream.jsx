import { Link, useNavigate } from "react-router-dom";
import { useState, useRef } from "react";

import { Group } from "../../components/Group/Group";
import { GroupWrapper } from "../../components/GroupWrapper/GroupWrapper";

import Logo from "../../assets/logo.png";
import Saly from "../../assets/saly.png";

import "./Stream.css";

export const Stream = () => {
  const [message, setMessage] = useState("");
  const [conversationId, setConversationId] = useState("");
  const [conversation, setConversation] = useState([]); // 사용자+AI 메시지 배열

  const eventSourceRef = useRef(null);
  const navigate = useNavigate();

  const handleChat = () => {
    if (!message.trim()) return;

    // 사용자 메시지 기록
    setConversation(prev => [...prev, { role: "user", content: message }]);

    // 기존 EventSource 종료
    if (eventSourceRef.current) {
      eventSourceRef.current.close();
    }

    // GET 파라미터 생성
    const params = new URLSearchParams({
      message,
      conversationId: conversationId.trim() ? conversationId : undefined
    });

    // SSE 요청 (Vite proxy 활용)
    eventSourceRef.current = new EventSource(
      `/api/chat/completions/stream?${params.toString()}`
    );

    // SSE 메시지 수신
    eventSourceRef.current.onmessage = (event) => {
      if (!event.data) return;

      setConversation(prev => {
        const last = prev[prev.length - 1];
        // 마지막 메시지가 AI가 아니면 새 메시지 추가
        if (!last || last.role !== "assistant") {
          return [...prev, { role: "assistant", content: event.data }];
        }
        // 마지막 AI 메시지에 이어 붙이기
        const updated = [...prev];
        updated[updated.length - 1].content += event.data;
        return updated;
      });
    };

    // SSE 에러 처리
    eventSourceRef.current.onerror = (err) => {
      console.error("SSE error:", err);
      eventSourceRef.current.close();
    };

    // 입력 초기화
    setMessage("");
  };

  return (
    <div className="stream" data-model-id="858:453">
      <img
        className="image"
        alt="Logo"
        src={Logo}
        onClick={() => navigate("/main")}
        style={{ cursor: "pointer" }}
      />

      <div className="group-3">
        <div className="group-4">
          <div className="group-5">
            {conversation.map((msg, idx) => (
              <div key={idx}>
                <div className="text-wrapper-2">
                  {msg.role === "user" ? "You: " : "AI: "} {msg.content}
                </div>
              </div>
            ))}
          </div>
        </div>

        <img className="saly" alt="Saly" src={Saly} />
      </div>

      <div className="group-7">
        <div className="text-wrapper-5">Send stream message</div>

        <div className="group-8">
          <div className="group-9">
            <div className="group-10">
              <div className="rectangle-3" />
              <input
                type="text"
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

export default Stream;