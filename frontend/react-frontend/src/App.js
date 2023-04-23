import logo from './logo.svg';
import './App.css';
import Form from './Form'
import Graph from './Graph';

function App() {
  return (
    <div className="App">
      <h2>Critical path method</h2>
      <h2>--HERE FORM | TODO--</h2>
      <div>
        <Form />
        <div className="container">
          <div className="row">
            <div className="col-10 mt-4">
              <Graph/>
            </div>
          </div>
        </div>
        
      </div>
    </div>
  );
}

export default App;
