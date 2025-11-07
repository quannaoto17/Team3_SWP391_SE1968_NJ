/**
 * AI Suggest Feature for Build PC
 * Handles AI-powered build suggestions based on user requirements
 */

(function() {
    'use strict';

    // DOM Elements
    const btnShowAiAssistant = document.getElementById('btnShowAiAssistant');
    const btnBackToOptions = document.getElementById('btnBackToOptions');
    const aiAssistantSection = document.getElementById('aiAssistantSection');
    const buildOptions = document.querySelector('.build-options');

    const btnAiSuggest = document.getElementById('btnAiSuggest');
    const userRequestInput = document.getElementById('userRequest');
    const aiResultSection = document.getElementById('aiResultSection');
    const aiResultContent = document.getElementById('aiResultContent');
    const aiErrorSection = document.getElementById('aiErrorSection');
    const btnApplyBuild = document.getElementById('btnApplyBuild');
    const btnNewSuggestion = document.getElementById('btnNewSuggestion');

    const optionCards = document.querySelectorAll('.option-card');

    // State
    let currentBuildPlan = null;
    let selectedBuildType = null;

    /**
     * Initialize event listeners
     */
    function init() {
        if (btnShowAiAssistant) {
            btnShowAiAssistant.addEventListener('click', showAiAssistant);
        }

        if (btnBackToOptions) {
            btnBackToOptions.addEventListener('click', hideAiAssistant);
        }

        if (btnAiSuggest) {
            btnAiSuggest.addEventListener('click', handleAiSuggest);
        }

        if (btnApplyBuild) {
            btnApplyBuild.addEventListener('click', handleApplyBuild);
        }

        if (btnNewSuggestion) {
            btnNewSuggestion.addEventListener('click', handleNewSuggestion);
        }

        // Quick option cards
        optionCards.forEach(card => {
            card.addEventListener('click', function() {
                handleQuickOption(this);
            });
        });

        // Allow Enter key to submit in textarea (with Ctrl/Cmd)
        if (userRequestInput) {
            userRequestInput.addEventListener('keydown', (e) => {
                if (e.key === 'Enter' && (e.ctrlKey || e.metaKey)) {
                    e.preventDefault();
                    handleAiSuggest();
                }
            });
        }
    }

    /**
     * Show AI Assistant section
     */
    function showAiAssistant() {
        if (buildOptions) buildOptions.style.display = 'none';
        if (aiAssistantSection) aiAssistantSection.style.display = 'block';
    }

    /**
     * Hide AI Assistant section and go back to options
     */
    function hideAiAssistant() {
        if (aiAssistantSection) aiAssistantSection.style.display = 'none';
        if (buildOptions) buildOptions.style.display = 'flex';

        // Reset state
        hideResults();
        hideError();
        clearSelectedOption();
        if (userRequestInput) userRequestInput.value = '';
        currentBuildPlan = null;
    }

    /**
     * Handle quick option card selection
     */
    function handleQuickOption(card) {
        const buildType = card.getAttribute('data-type');
        selectedBuildType = buildType;

        // Update visual selection
        optionCards.forEach(c => c.classList.remove('selected'));
        card.classList.add('selected');

        // Auto-fill textarea with build type request
        const requestTexts = {
            'gaming-high': 'High-end gaming PC for 4K gaming with maximum graphics settings, budget around $2000-2500',
            'gaming-mid': 'Mid-range gaming PC for smooth 1080p/1440p gaming, budget around $1000-1500',
            'workstation': 'Professional workstation for video editing, 3D rendering, and photo editing, budget around $1500-2000',
            'office': 'Office PC for daily work tasks, web browsing, and document processing, budget around $600-800',
            'budget': 'Budget-friendly PC with best performance for the price, budget under $700',
            'streaming': 'Gaming and streaming PC for simultaneous gaming and live broadcasting, budget around $1800-2200'
        };

        if (userRequestInput && requestTexts[buildType]) {
            userRequestInput.value = requestTexts[buildType];
        }

        // Scroll to textarea
        userRequestInput.scrollIntoView({ behavior: 'smooth', block: 'center' });
    }

    /**
     * Clear selected option
     */
    function clearSelectedOption() {
        optionCards.forEach(c => c.classList.remove('selected'));
        selectedBuildType = null;
    }

    /**
     * Handle AI suggest button click
     */
    async function handleAiSuggest() {
        const userRequest = userRequestInput.value.trim();

        if (!userRequest) {
            showError('Please enter your requirements or select a PC type.');
            return;
        }

        // Show loading state
        setLoadingState(true);
        hideError();
        hideResults();

        try {
            // Call API to get AI suggestion
            const response = await fetch('/api/build/ai-suggest', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({ userRequest: userRequest })
            });

            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }

            const buildPlan = await response.json();
            currentBuildPlan = buildPlan;

            // Display results
            displayBuildPlan(buildPlan);
            showResults();

            // Scroll to results
            aiResultSection.scrollIntoView({ behavior: 'smooth', block: 'start' });

        } catch (error) {
            console.error('Error getting AI suggestion:', error);
            showError('Failed to get AI suggestion. Please try again. Error: ' + error.message);
        } finally {
            setLoadingState(false);
        }
    }

    /**
     * Display the build plan from AI
     */
    function displayBuildPlan(buildPlan) {
        let html = '';

        // Motherboard
        if (buildPlan.planMotherboard) {
            html += createComponentRuleHTML('🔌 Motherboard', buildPlan.planMotherboard);
        }

        // CPU
        if (buildPlan.planCpu) {
            html += createComponentRuleHTML('🧠 CPU', buildPlan.planCpu);
        }

        // GPU
        if (buildPlan.planGpu) {
            html += createComponentRuleHTML('🎮 GPU', buildPlan.planGpu);
        }

        // RAM
        if (buildPlan.planRam) {
            html += createComponentRuleHTML('💾 RAM', buildPlan.planRam);
        }

        // Storage
        if (buildPlan.planStorage) {
            html += createComponentRuleHTML('💿 Storage', buildPlan.planStorage);
        }

        // PSU
        if (buildPlan.planPsu) {
            html += createComponentRuleHTML('⚡ PSU', buildPlan.planPsu);
        }

        // Case
        if (buildPlan.planCase) {
            html += createComponentRuleHTML('🏠 Case', buildPlan.planCase);
        }

        // Cooling
        if (buildPlan.planCooling) {
            html += createComponentRuleHTML('❄️ Cooling', buildPlan.planCooling);
        }

        if (!html) {
            html = '<p>Could not generate build plan. Please try with different requirements.</p>';
        }

        aiResultContent.innerHTML = html;
    }

    /**
     * Create HTML for a component rule
     */
    function createComponentRuleHTML(componentName, rule) {
        return `
            <div class="component-rule">
                <h5>${componentName}</h5>
                <p><strong>Max Budget:</strong> ${formatCurrency(rule.budetMax)}</p>
                <p><strong>Performance Score:</strong> ${rule.scoreMin} - ${rule.scoreMax}</p>
            </div>
        `;
    }

    /**
     * Handle apply build button click
     */
    function handleApplyBuild() {
        if (!currentBuildPlan) {
            showError('No build plan to apply.');
            return;
        }

        // Store build plan in session storage
        sessionStorage.setItem('aiBuildPlan', JSON.stringify(currentBuildPlan));

        // Redirect to mainboard selection to start the build process
        window.location.href = '/build/mainboard';
    }

    /**
     * Handle new suggestion button click
     */
    function handleNewSuggestion() {
        hideResults();
        clearSelectedOption();
        userRequestInput.value = '';
        userRequestInput.focus();
        currentBuildPlan = null;

        // Scroll back to options
        document.querySelector('.quick-options').scrollIntoView({ behavior: 'smooth', block: 'start' });
    }

    /**
     * Format currency (USD)
     */
    function formatCurrency(amount) {
        if (!amount) return 'N/A';
        return new Intl.NumberFormat('en-US', {
            style: 'currency',
            currency: 'USD',
            minimumFractionDigits: 0,
            maximumFractionDigits: 0
        }).format(amount);
    }

    /**
     * Show/hide loading state
     */
    function setLoadingState(isLoading) {
        const btnText = btnAiSuggest.querySelector('.btn-text');
        const btnLoading = btnAiSuggest.querySelector('.btn-loading');

        if (isLoading) {
            btnText.style.display = 'none';
            btnLoading.style.display = 'inline';
            btnAiSuggest.disabled = true;
        } else {
            btnText.style.display = 'inline';
            btnLoading.style.display = 'none';
            btnAiSuggest.disabled = false;
        }
    }

    /**
     * Show error message
     */
    function showError(message) {
        aiErrorSection.querySelector('.error-message').textContent = message;
        aiErrorSection.style.display = 'block';
        aiErrorSection.scrollIntoView({ behavior: 'smooth', block: 'center' });
    }

    /**
     * Hide error message
     */
    function hideError() {
        aiErrorSection.style.display = 'none';
    }

    /**
     * Show results section
     */
    function showResults() {
        aiResultSection.style.display = 'block';
    }

    /**
     * Hide results section
     */
    function hideResults() {
        aiResultSection.style.display = 'none';
    }

    // Initialize on DOM ready
    if (document.readyState === 'loading') {
        document.addEventListener('DOMContentLoaded', init);
    } else {
        init();
    }

})();

