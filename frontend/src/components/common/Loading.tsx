export default function Loading() {
    return (
        <div className="flex justify-center items-center h-screen bg-gray-50">
            <div className="text-center">
                <div className="w-16 h-16 border-4 border-blue-500 border-t-transparent rounded-full animate-spin mx-auto mb-4" />
                <p className="text-gray-700 text-base">불러오는 중입니다...</p>
            </div>
        </div>
    );
}
