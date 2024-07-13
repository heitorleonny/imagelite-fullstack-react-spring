import React from "react";


interface ButtronProps {
    style: string;
    label?: string;
    onCLick?: (event: any) => void;
    type?: "submit" | "button" | "reset" | undefined;
}

export const Button: React.FC<ButtronProps> = ({style, label, onCLick, type}: ButtronProps) => {
    return (
        <button className={`${style} text-white px-4 py-2 rounded-lg `} onClick={onCLick} type={type}>{label}</button>
    )
}