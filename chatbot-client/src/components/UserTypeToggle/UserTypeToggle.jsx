import "./UserTypeToggle.css";

const UserTypeToggle = ({ value, onChange }) => {
    return (
        <div className="user-type-toggle">
            <button
                type="button"
                className={`toggle-btn ${value === "USER" ? "active" : ""}`}
                onClick={() => onChange("USER")}
            >
                User
            </button>

            <button
                type="button"
                className={`toggle-btn ${value === "ADMIN" ? "active" : ""}`}
                onClick={() => onChange("ADMIN")}
            >
                Admin
            </button>
        </div>
    );
};

export default UserTypeToggle;