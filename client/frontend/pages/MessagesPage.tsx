import React from 'react';
import Sidebar from '../components/Sidebar'
import Chat from '../components/Chat'
import Header from '../components/Header';
import '../src/MessagesPage.css';

const MessagingPage: React.FC = () => {
    // Dummy messages data for demonstration

    return (
        <div id="container" style={{marginTop: '10vh'}}>
            <aside>
                <header>
                    <input type="text" placeholder="search"/>
                </header>
                <ul>
                    <li>
                        <img src="https://s3-us-west-2.amazonaws.com/s.cdpn.io/1940306/chat_avatar_01.jpg" alt=""/>
                        <div className="hover-che">
                            <h2>Jack Shoparrow</h2>
                            <h3>
                                <span className="status orange"></span>
                                offline
                            </h3>
                        </div>
                    </li>
                    <li>
                        <img src="https://s3-us-west-2.amazonaws.com/s.cdpn.io/1940306/chat_avatar_02.jpg" alt=""/>
                        <div className="hover-che">
                            <h2>Ivan Cirakov</h2>
                            <h3>
                                <span className="status green"></span>
                                online
                            </h3>
                        </div>
                    </li>
                    <li>
                        <img src="https://s3-us-west-2.amazonaws.com/s.cdpn.io/1940306/chat_avatar_03.jpg" alt=""/>
                        <div className="hover-che">
                            <h2>Niki Naglev</h2>
                            <h3>
                                <span className="status orange"></span>
                                offline
                            </h3>
                        </div>
                    </li>
                    <li>
                        <img src="https://s3-us-west-2.amazonaws.com/s.cdpn.io/1940306/chat_avatar_04.jpg" alt=""/>
                        <div className="hover-che">
                            <h2>Viktoriq Secret</h2>
                            <h3>
                                <span className="status green"></span>
                                online
                            </h3>
                        </div>
                    </li>
                    <li>
                        <img src="https://s3-us-west-2.amazonaws.com/s.cdpn.io/1940306/chat_avatar_05.jpg" alt=""/>
                        <div className="hover-che">
                            <h2>Sasha Velichkova</h2>
                            <h3>
                                <span className="status orange"></span>
                                offline
                            </h3>
                        </div>
                    </li>
                    <li>
                        <img src="https://s3-us-west-2.amazonaws.com/s.cdpn.io/1940306/chat_avatar_06.jpg" alt=""/>
                        <div className="hover-che">
                            <h2>Prénom Nom</h2>
                            <h3>
                                <span className="status green"></span>
                                online
                            </h3>
                        </div>
                    </li>
                    <li>
                        <img src="https://s3-us-west-2.amazonaws.com/s.cdpn.io/1940306/chat_avatar_07.jpg" alt=""/>
                        <div className="hover-che">
                            <h2>Prénom Nom</h2>
                            <h3>
                                <span className="status green"></span>
                                online
                            </h3>
                        </div>
                    </li>
                </ul>
            </aside>
            <main>
                <header>
                    <img src="https://s3-us-west-2.amazonaws.com/s.cdpn.io/1940306/chat_avatar_01.jpg" alt=""/>
                    <div>
                        <h2>Chat with Jack Shoparrow</h2>
                        <h3>already 1902 messages</h3>
                    </div>
                    <img src="https://s3-us-west-2.amazonaws.com/s.cdpn.io/1940306/ico_star.png" alt=""/>
                </header>
                <ul id="chat">
                    <li className="you">
                        <div className="entete">
                            <span className="status green"></span>
                            <h2>Vincent</h2>
                            <h3>10:12AM, Today</h3>
                        </div>
                        <div className="triangle"></div>
                        <div className="message">
                            Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor.
                        </div>
                    </li>
                    <li className="me">
                        <div className="entete">
                            <h3>10:12AM, Today</h3>
                            <h2>Vincent</h2>
                        </div>
                        <div className="triangle"></div>
                        <div className="message">
                            Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor.
                        </div>
                    </li>
                    <li className="me">
                        <div className="entete">
                            <h3>10:12AM, Today</h3>
                            <h2>Vincent</h2>
                        </div>
                        <div className="triangle"></div>
                        <div className="message">
                            OK
                        </div>
                    </li>
                    <li className="you">
                        <div className="entete">
                            <h2>Vincent</h2>
                            <h3>10:12AM, Today</h3>
                        </div>
                        <div className="triangle"></div>
                        <div className="message">
                            Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor.
                        </div>
                    </li>
                    <li className="me">
                        <div className="entete">
                            <h3>10:12AM, Today</h3>
                            <h2>Vincent</h2>
                        </div>
                        <div className="triangle"></div>
                        <div className="message">
                            Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor.
                        </div>
                    </li>
                    <li className="me">
                        <div className="entete">
                            <h3>10:12AM, Today</h3>
                            <h2>Vincent</h2>
                        </div>
                        <div className="triangle"></div>
                        <div className="message">
                            OK
                        </div>
                    </li>
                </ul>
                <footer>
                    <textarea placeholder="Type your message"></textarea>
                    <img src="https://s3-us-west-2.amazonaws.com/s.cdpn.io/1940306/ico_picture.png" alt=""/>
                    <img src="https://s3-us-west-2.amazonaws.com/s.cdpn.io/1940306/ico_file.png" alt=""/>
                    <a href="#">Send</a>
                </footer>
            </main>
        </div>
    );
};

export default MessagingPage;
