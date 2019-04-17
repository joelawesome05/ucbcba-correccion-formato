import React, { Component } from 'react';
import PDF from 'react-pdf-js';

import StepCover from "./wizards/StepCover"
import StepGeneralIndex from "./wizards/StepGeneralIndex"
import StepFigureIndex from "./wizards/StepFigureIndex"
import StepTableIndex from "./wizards/StepTableIndex"
import StepBiography from "./wizards/StepBiography"
import StepAnnexed from "./wizards/StepAnnexed"



var url = " ";
class PagesForm extends Component {

    constructor(props) {
        super(props);
        this.state = {
            currentStep: 1,
            coverPage: 0,
            generalIndexPageStart: 0,
            generalIndexPageEnd: 0,
            figureIndexPageStart: 0,
            figureIndexPageEnd: 0,
            tableIndexPageStart: 0,
            tableIndexPageEnd: 0,
            biographyPageStart: 0,
            biographyPageEnd: 0,
            annexedPageStart: 0,
            annexedPageEnd: 0,
            isLoading: true,
        };
        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
        this.nextStep = this.nextStep.bind(this)
        this.previousStep = this.previousStep.bind(this)
        url = "/api/downloadFile/" + `${encodeURI(this.props.match.params.name)}`;
    }

    async componentDidMount() {
        var pages = await (await fetch(`/api/getPages/${encodeURI(this.props.match.params.name)}`)).json();
        this.setState({
            coverPage: pages[0],
            generalIndexPageStart: pages[1],
            generalIndexPageEnd: pages[2],
            figureIndexPageStart: pages[3],
            figureIndexPageEnd: pages[4],
            tableIndexPageStart: pages[5],
            tableIndexPageEnd: pages[6],
            biographyPageStart: pages[7],
            biographyPageEnd: pages[8],
            annexedPageStart: pages[9],
            annexedPageEnd: pages[10]
        });
        this.setState({ isLoading: false });
    }

    handleChange(event) {
        this.setState({ [event.target.name]: event.target.type === 'number' ? parseInt(event.target.value) : event.target.value });
    }


    handleSubmit(event) {
        event.preventDefault();
        const searchParams = new URLSearchParams();
        searchParams.set("coverPage", this.state.coverPage);
        searchParams.set("generalIndexPageStart", this.state.generalIndexPageStart);
        searchParams.set("generalIndexPageEnd", this.state.generalIndexPageEnd);
        searchParams.set("figureIndexPageStart", this.state.figureIndexPageStart);
        searchParams.set("figureIndexPageEnd", this.state.figureIndexPageEnd);
        searchParams.set("tableIndexPageStart", this.state.tableIndexPageStart);
        searchParams.set("tableIndexPageEnd", this.state.tableIndexPageEnd);
        searchParams.set("biographyPageStart", this.state.biographyPageStart);
        searchParams.set("biographyPageEnd", this.state.biographyPageEnd);
        searchParams.set("annexedPageStart", this.state.annexedPageStart);
        searchParams.set("annexedPageEnd", this.state.annexedPageEnd);
        const parameters = searchParams.toString();
        setTimeout(function () { this.props.history.push(`/verResultados/${encodeURI(this.props.match.params.name)}` + `?${parameters}`) }.bind(this), 2000);
    }

    nextStep() {
        let currentStep = this.state.currentStep
        currentStep = currentStep >= 5 ? 6 : currentStep + 1
        this.setState({
            currentStep: currentStep
        })
    }

    previousStep() {
        let currentStep = this.state.currentStep
        currentStep = currentStep <= 1 ? 1 : currentStep - 1
        this.setState({
            currentStep: currentStep
        })
    }

    get previousButton() {
        let currentStep = this.state.currentStep;
        if (currentStep !== 1) {
            return (
                <button
                    className="btn btn-secondary"
                    type="button" onClick={this.previousStep}>
                    Anterior
                </button>
            )
        }
        return (
            <button
                className="btn btn-secondary disabled"
                type="button" >
                Anterior
            </button>
        );
    }

    get nextButton() {
        let currentStep = this.state.currentStep;
        if (currentStep < 6) {
            return (
                <button
                    className="btn btn-primary"
                    type="button" onClick={this.nextStep}>
                    Siguiente
                </button>
            )
        }
        return (
            <button
                className="btn btn-primary disabled"
                type="button">
                Siguiente
            </button>
        );
    }

    render() {
        if (this.state.isLoading) {
            return <p style={{ color: "black" }}>Cargando...</p>;
        }
        return (
            <div>
                <center>
                    <h3> Sistema de ayuda para la detección y corrección de errores de formato en trabajos académicos</h3>
                </center>
                <a href="/">
                    Volver al Inicio
                </a>
                <p>Paso {this.state.currentStep} </p>

                <form onSubmit={this.handleSubmit}>

                    <StepCover
                        currentStep={this.state.currentStep}
                        handleChange={this.handleChange}
                        coverPage={this.state.coverPage}
                        url={url}
                    />

                    <StepGeneralIndex
                        currentStep={this.state.currentStep}
                        handleChange={this.handleChange}
                        generalIndexPageStart={this.state.generalIndexPageStart}
                        generalIndexPageEnd={this.state.generalIndexPageEnd}
                        url={url}
                    />

                    <StepFigureIndex
                        currentStep={this.state.currentStep}
                        handleChange={this.handleChange}
                        figureIndexPageStart={this.state.figureIndexPageStart}
                        figureIndexPageEnd={this.state.figureIndexPageEnd}
                        url={url}
                    />

                    <StepTableIndex
                        currentStep={this.state.currentStep}
                        handleChange={this.handleChange}
                        tableIndexPageStart={this.state.tableIndexPageStart}
                        tableIndexPageEnd={this.state.tableIndexPageEnd}
                        url={url}
                    />

                    <StepBiography
                        currentStep={this.state.currentStep}
                        handleChange={this.handleChange}
                        biographyPageStart={this.state.biographyPageStart}
                        biographyPageEnd={this.state.biographyPageEnd}
                        url={url}
                    />

                    <StepAnnexed
                        currentStep={this.state.currentStep}
                        handleChange={this.handleChange}
                        annexedPageStart={this.state.annexedPageStart}
                        annexedPageEnd={this.state.annexedPageEnd}
                        url={url}
                    />

                    <center>
                        {this.previousButton}
                        {this.nextButton}
                    </center>

                </form>
            </div>
        );
    }
}

export default PagesForm;
