import React, { Component } from 'react';
import PDF from 'react-pdf-js';
import PdfPreview from "./PdfPreview";

class StepFigureIndex extends Component {
    constructor(props) {
        super(props);
        this.state = {
            totalPages: null
        };
    }

    onDocumentComplete = (pages) => {
        this.setState({ totalPages: pages });
    }

    render() {
        if (this.props.currentStep !== 3) {
            return null
        }

        return (
            <div className="form-group">
                <center><h4> Índice de Figuras </h4>
                    <label >Página inicial:</label>
                    <input
                        name="figureIndexPageStart"
                        type="number"
                        value={this.props.figureIndexPageStart}
                        onChange={this.props.handleChange}
                    />
                    <label >Página final:</label>
                    <input
                        name="figureIndexPageEnd"
                        type="number"
                        value={this.props.figureIndexPageEnd}
                        onChange={this.props.handleChange}
                    />
                    <br></br>
                </center>
                <center>
                    <PdfPreview
                        url={this.props.url}
                        pageStart={this.props.figureIndexPageStart}
                        pageEnd={this.props.figureIndexPageEnd}
                    />
                </center>
            </div>
        )
    }
}

export default StepFigureIndex;
