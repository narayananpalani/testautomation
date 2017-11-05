Feature: Radio Nav Drawers

AS A user

I WANT to see more content associated with navigation links

So THAT I can easily find what I am looking for

Scenario Outline: Opening and closing the drawers

Given I can see the radio nav

When I select <section> in the radio nav

Then the <section> drawer is open

When I select <section> in the radio nav again

Then the <section> drawer is closed

Examples: of sections

| section|

| Stations |

| Categories |

| Schedules |

@todo

Scenario Outline: Opening a drawer should close the other drawers

Given I can see the radio nav

When I select <section> in the radio nav

Then the <section> drawer is open and the other drawers are closed

Examples: of sections

| section |

| Stations |

| Categories |

| Schedules |

@todo

Scenario: Selecting categories displays categories links

Given I can see the radio nav

When I select Categories in the radio nav

Then I can see the following categories

| Comedy |

| Drama |

| Factual |

| Music |

| News |

| Science & Nature |

| History |

| Arts & Culture |

| Politics |

| Entertainment |

| Religion |

| Performances & Events |

| Documentaries |

| Readings |

| Podcasts |

@todo

Scenario: Selecting all categories navigates to all categories page

Given I can see the radio nav

When I select Categories in the radio nav

And I select the all categories link

Then I am on the all categories page