import React from 'react';
import { Link, Navigate } from 'react-router-dom';
import '../src/LoginPage.css'

// const LoginPage: React.FC = () => {
//     return (
//         <div className="login-container">
//             <p>Login</p>
//             <h2>Login or Register</h2>
//             <form className="login-form">
//                 <input
//                     type="email"
//                     placeholder="Email"
//                     value=""
//                     // onChange={(e) => setEmail(e.target.value)}
//                     required
//                 />
//                 <input
//                     type="password"
//                     placeholder="Password"
//                     value=""
//                     // onChange={(e) => setPassword(e.target.value)}
//                     required
//                 />
//                 <button type="submit">Login</button>
//                 {/*{error && <p className="error-message">{error}</p>}*/}
//             </form>
//             <p className="register-link">
//                 New user? <Link to="/register">Register here</Link>
//             </p>
//         </div>
//     );
// };
//
// export default LoginPage;
const LoginPage:React.FC = () => {
    return (
        <div className="container">
            <div className="login-container">
                <h1>Login</h1>
                <form id="loginForm">
                    <div className="input-group">
                        <label htmlFor="username">Username</label>
                        <input className="input-login" type="text" id="username" name="username" required/>
                    </div>
                    <div className="input-group">
                        <label htmlFor="password">Password</label>
                        <input className="input-login" type="password" id="password" name="password" required/>
                    </div>
                    <button className="button-login" type="submit">Login</button>
                </form>
                <p style={{fontSize:'12px', marginTop:'10px', textAlign:'left'}}>
                    New user?
                    <Link to="/register" id="link-to-register">Register here</Link>
                </p>
            </div>
        </div>
    );
}

export default LoginPage;