interface fieldErrorProps {
    error: any | null;
}

export const FieldError: React.FC<fieldErrorProps> = ({
    error
}) => {
    if (error){
        return (
            <span className="text-red-500 text-sm">{error}</span>
        )
    }
}