import "./GroupWrapper.css";

export const GroupWrapper = ({ className, rectangleClassName, text, onClick }) => {

    const handleClick = async () => {
        if (onClick) {
            await onClick();
        }
    }

    return (
        <div className={`group-wrapper ${className}`} onClick={handleClick} style={{ cursor: "pointer" }}>
            <div className="group-2">
                <div className={`rectangle-2 ${rectangleClassName}`} />
                <div className="text-wrapper">{text}</div>
            </div>
        </div>
    );
};

export default GroupWrapper;