import React from 'react';
import {Link} from 'react-router-dom';
import datePic from '../src/assets/dating_pic.png';
import '../src/index.css'

const DatingPage: React.FC = () => {
    return (
        <div className="container">
            <img src={datePic} alt="Sample Date"
                 style={{width: '400px', height: '80vh', zIndex: '999', borderRadius: '5px'}}/>

            <div style={{display: 'flex', position: 'absolute', zIndex: '999', bottom: '1%', width:'400px', justifyContent:'space-between'}}>
                <button className="butoncheta btn mr-2" style={{marginRight:'150px'}}>
                    <img src="../src/assets/decline.png" alt="decline" style={{width: '80px', height: '80px'}}/>
                </button>
                {/*<button className="butoncheta btn mr-2">
                    <img src="../src/assets/favorite.png" alt="favorite" style={{width: '80px', height: '80px'}}/>
                </button>*/}
                <button className=" butoncheta btn">
                    <img src="../src/assets/love.png" alt="love" style={{width: '60px', height: '60px'}}/>
                </button>
            </div>
            <Link to="/messages">
                <button className="btn btn-primary position-fixed" style={{
                    padding: '15px',
                    bottom: '20px',
                    right: '20px',
                    marginBottom: '30px',
                    marginRight: "30px",
                    backgroundColor: 'white',
                    border: 'white',
                    borderRadius: '30px'
                }}>
                    <img src="../src/assets/chat.png" alt="chat" style={{height: '30px', width: '30px'}}/>
                </button>
            </Link>
        </div>
    );
};

export default DatingPage;
