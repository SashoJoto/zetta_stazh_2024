import React, { createContext, useContext, useState, ReactNode } from 'react';

interface AuthContextType {
    user: any;
    login: (user: any) => void;
    logout: () => void;
}

const AuthContext = createContext<AuthContextType | undefined>(undefined);

const AuthProvider: React.FC<{ children: ReactNode }> = ({ children }) => {
    const [user, setUser] = useState<any>(null);

    const login = (userData: any) => {
        setUser(userData);
        // You can also save the user data to localStorage/sessionStorage if needed
    };

    const logout = () => {
        setUser(null);
        // You can also remove the user data from localStorage/sessionStorage if needed
    };

    return (
        <AuthContext.Provider value={{ user, login, logout }}>
            {children}
        </AuthContext.Provider>
    );
};

const useAuth = () => {
    const context = useContext(AuthContext);
    if (context === undefined) {
        throw new Error('useAuth must be used within an AuthProvider');
    }
    return context;
};

export { AuthProvider, useAuth };
